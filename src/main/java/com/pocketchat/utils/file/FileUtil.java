package com.pocketchat.utils.file;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.server.exceptions.general.StringEmptyException;
import com.pocketchat.utils.server_shutdown.ServerShutdownUtil;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String today = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));

    private final ServerShutdownUtil serverShutdownUtil;

    @Value("${server.file.windows.base.directory}")
    private String windowsBaseDirectory;

    @Value("${server.file.linux.base.directory}")
    private String linuxBaseDirectory;

    @Value("${server.file.macos.base.directory}")
    private String macOSBaseDirectory;

    @Autowired
    FileUtil(ServerShutdownUtil serverShutdownUtil) {
        this.serverShutdownUtil = serverShutdownUtil;
        checkBaseDirectory();
    }

    /**
     * Upload MultipartFile object to the given file directory with random generated file name.
     *
     * @param multipartFile:   MultipartFile object.
     * @param moduleDirectory: String = The directory of a module. For example,
     *                         if you're finding an image in Promotion, your moduleDirectory should be promotion/.
     * @return an Multimedia object contains the File object transferred from @param multipartFile object.
     * @throws IOException if anything happened during transferToFile() process.
     */
    public Multimedia createMultimedia(MultipartFile multipartFile, String moduleDirectory) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename();

        String fileName = generateNewFileName(originalFileName);

        return transferToFile(multipartFile, moduleDirectory, fileName);
    }

    /**
     * Check and build the base directory in the host environment.
     * Will create the directory based on OS.
     * Will perform server shutdown if the app is unable to create the directory.
     */
    private void checkBaseDirectory() {
        Path path;
        String directory = getBaseDirectory();

        if (StringUtils.isEmpty(directory)) {
            logger.error("OS Not Supported for File Upload/Download. Shutting down server now.");
            serverShutdownUtil.initiateShutdown(0);
        }

        path = Paths.get(directory);

        if (!ObjectUtils.isEmpty(path) && Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Error during create directory: {}, reason: {}", directory, e.getMessage());
                logger.error("Unable to create directory. Shutting down server now.");
                serverShutdownUtil.initiateShutdown(1);
            }
        }
    }

    /**
     * Get current base directory, based on the OS environment that the app is deployed in.
     * @return Directory in String value.
     */
    private String getBaseDirectory() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return windowsBaseDirectory;
        } else if (SystemUtils.IS_OS_LINUX) {
            return linuxBaseDirectory;
        } else if (SystemUtils.IS_OS_MAC) {
            return macOSBaseDirectory;
        }

        logger.info("SystemUtils.IS_OS_WINDOWS: {} ", SystemUtils.IS_OS_WINDOWS);
        logger.info("SystemUtils.IS_OS_LINUX: {} ", SystemUtils.IS_OS_LINUX);
        logger.info("SystemUtils.IS_OS_MAC: {} ", SystemUtils.IS_OS_MAC);
        logger.info("Getting Nothing?????");
        return "";
    }

    /**
     * Clone an Multimedia object, with an option to create a directory for the new File.
     *
     * @param existingMultimedia: Multimedia = Original Multimedia object that you want to copy.
     * @param moduleDirectory:      String = The directory of a module. For example,
     *                              if you're finding an image in Promotion, your moduleDirectory should be promotion/.
     * @return new Multimedia object. Different file name with given @param fileDirectory.
     * @throws IOException if getTargetFile() has problem.
     */
    public Multimedia cloneMultimedia(Multimedia existingMultimedia, String moduleDirectory) throws IOException {
        if(!StringUtils.isEmpty(moduleDirectory)) {
            throw new StringEmptyException("Module Directory is empty!");
        }

        File existingFile = getFileWithAbsolutePath(moduleDirectory, existingMultimedia.getFileDirectory(),
                existingMultimedia.getFileName());

        Multimedia newMultimedia = Multimedia.builder()
                .contentType(existingMultimedia.getContentType())
                .fileDirectory(today)
                .fileSize(existingMultimedia.getFileSize())
                .fileName(generateNewFileName(existingMultimedia.getFileName()))
                .fileExtension(existingMultimedia.getFileExtension())
                .build();

        File newFile = getTargetFile(moduleDirectory, newMultimedia.getFileName());

//        FileUtils.copyFile(existingFile, newFile);

        return newMultimedia;
    }

    /**
     * Delete an Multimedia object's File in the server.
     *
     * @param uploadedFile:    Multimedia object
     * @param moduleDirectory: String = The directory of a module. For example,
     *                         if you're finding an image in Promotion, your moduleDirectory should be promotion/.
     * @return boolean to the File is deleted or not.
     */
    public boolean deleteFile(Multimedia uploadedFile, String moduleDirectory) throws FileNotFoundException {
//        File toBeDeleted = getFileWithAbsolutePath(moduleDirectory, uploadedFile.getFileDirectory(), uploadedFile.getFileName());
//        return FileUtils.deleteQuietly(toBeDeleted);
        return false;
    }

    /**
     * Generate a random file name.
     *
     * @param fileName: The name of the file with file extension attached.
     * @return string of the new random generated file name.
     */
    private String generateNewFileName(String fileName) {
        String fileExt = getFileExtension(fileName);
        return UUID.randomUUID().toString().replaceAll("-", "") + fileExt;
    }

    /**
     * Get the file's extension.
     * @param fileName : File name. Typically from multipartFile or Multimedia.getFileName().
     * @return Extension name of the file. For example, XXXX.mp4 (XXXX not included)
     */
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * Convert a MultipartFile object into a new Multimedia object.
     *
     * @param multipartFile:   MultipartFile object.
     * @param moduleDirectory: String = The directory of a module. For example,
     *                         if you're finding an image in Promotion, your moduleDirectory should be promotion/.
     * @param fileName:        String = The name of the file in the server.
     * @return An Multimedia object that consist the information of the File that @param multipartFile transferred to.
     * @throws IOException: In case any error happened during MultipartFile.transferTo(), such as permission denied.
     */
    private Multimedia transferToFile(MultipartFile multipartFile, String moduleDirectory, String fileName) throws IOException {
        File newFile = getTargetFile(moduleDirectory, fileName);
        multipartFile.transferTo(newFile);
        return Multimedia.builder()
                .contentType(multipartFile.getContentType())
                .fileDirectory(today)
                .fileName(fileName)
                .fileSize(multipartFile.getSize())
                .fileExtension(getFileExtension(fileName))
                .build();
    }

    /**
     * Get the file based on the given Multimedia object.
     * This function will throw error if the file does not exist. (Specifically for the Multimedia objects)
     * Will NOT create the file if the file doesn't exist in the server.
     *
     * @param moduleDirectory: String = The directory of a module. For example,
     *                         if you're finding an image in Promotion, your moduleDirectory should be promotion/.
     * @param fileDirectory:   String = The directory of the file, excluding the file name.
     * @param fileName:        String = The name of the file in the server.
     * @return File object
     * @throws FileNotFoundException If the created File object doesn't exist.
     */
    public File getFileWithAbsolutePath(String moduleDirectory, String fileDirectory, String fileName) throws FileNotFoundException {
        if(StringUtils.isEmpty(moduleDirectory)) {
            throw new StringEmptyException("moduleDirectory shouldn't be empty");
        }

        if(StringUtils.isEmpty(fileDirectory)) {
            throw new StringEmptyException("fileDirectory shouldn't be empty");
        }

        if(StringUtils.isEmpty(fileName)) {
            throw new StringEmptyException("fileName shouldn't be empty");
        }

        File file = new File(getBaseDirectory() + moduleDirectory + fileDirectory + "/" + fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("Multimedia File not found" + ", moduleDirectory: " + moduleDirectory +
                    ", fileDirectory: " + fileDirectory + ", fileName: " + fileName);
        }

        return file;
    }

    /**
     * Get a file given the following params:
     * If the file doesn't exist, it will create the file with the given directory and the name.
     *
     * @param moduleDirectory: String = The directory of a module. For example,
     *                         if you're finding an image in Promotion, your moduleDirectory should be promotion/.
     * @param fileName:        String = The name of the file in the server.
     * @return File object
     * @throws IOException If FileUtils.forceMkdir has error such as Permission denied.
     */
    private File getTargetFile(String moduleDirectory, String fileName) throws IOException {
        if(StringUtils.isEmpty(moduleDirectory)) {
            throw new StringEmptyException("moduleDirectory shouldn't be empty");
        }

        if(StringUtils.isEmpty(fileName)) {
            throw new StringEmptyException("fileName shouldn't be empty");
        }

        String fullFileDirectory = getBaseDirectory() + moduleDirectory + today + "/";

        File filePath = new File(fullFileDirectory);

        if (!filePath.exists()) {
            FileUtils.forceMkdir(filePath);
        }

        return new File(filePath.getAbsolutePath() + "/" + fileName);
    }
}
