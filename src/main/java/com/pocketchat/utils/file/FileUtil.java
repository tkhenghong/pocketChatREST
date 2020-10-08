package com.pocketchat.utils.file;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.server.exceptions.general.StringEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String today = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));

    @Value("${server.file.base.directory}")
    private String baseDirectory;

    /**
     * Upload MultipartFile object to the given file directory with random generated file name.
     *
     * @param multipartFile:   MultipartFile object.
     * @param moduleDirectory: String = The directory of a module. For example,
     *                         if you're finding an image in Promotion, your moduleDirectory should be promotion/.
     * @return an Multimedia object contains the File object transferred from @param multipartFile object.
     * @throws IOException if anything happened during transferToFile() process.
     */
    public Multimedia uploadMultipartFile(MultipartFile multipartFile, String moduleDirectory) throws IOException {
//        String originalFileName = multipartFile.getOriginalFilename();
//
//        String fileName = generateNewFileName(originalFileName);
//
//        return transferToFile(multipartFile, moduleDirectory, fileName);
        return null;
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
//    public Multimedia cloneMultimedia(Multimedia existingMultimedia, String moduleDirectory) throws IOException {
//        if(!StringUtils.isEmpty(moduleDirectory)) {
//            throw new StringEmptyException("Module Directory is empty!");
//        }
//
//        File existingFile = getFileWithAbsolutePath(moduleDirectory, existingMultimedia.getFileDirectory(),
//                existingMultimedia.getFileName());
//
//        Multimedia newMultimedia = Multimedia.builder()
//                .withOriginalFileName(existingMultimedia.getOriginalFileName())
//                .withContentType(existingMultimedia.getContentType())
//                .withFileDirectory(today)
//                .withFileSize(existingMultimedia.getFileSize())
//                .withFileName(generateNewFileName(generateNewFileName(existingMultimedia.getFileName())))
//                .build();
//
//        File newFile = getTargetFile(moduleDirectory, newMultimedia.getFileName());
//
//        FileUtils.copyFile(existingFile, newFile);
//
//        return newMultimedia;
//    }

    /**
     * Delete an Multimedia object's File in the server.
     *
     * @param uploadedFile:    Multimedia object
     * @param moduleDirectory: String = The directory of a module. For example,
     *                         if you're finding an image in Promotion, your moduleDirectory should be promotion/.
     * @return boolean to the File is deleted or not.
     */
//    public boolean deleteFile(Multimedia uploadedFile, String moduleDirectory) throws FileNotFoundException {
//        File toBeDeleted = getFileWithAbsolutePath(moduleDirectory, uploadedFile.getFileDirectory(), uploadedFile.getFileName());
//        return FileUtils.deleteQuietly(toBeDeleted);
//    }

    /**
     * Generate a random file name.
     *
     * @param originalFileName: The name of the file with file extension attached.
     * @return string of the new random generated file name.
     */
//    private String generateNewFileName(String originalFileName) {
//        String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
//        return UUID.randomUUID().toString().replaceAll("-", "") + fileExt;
//    }

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
//    private Multimedia transferToFile(MultipartFile multipartFile, String moduleDirectory, String fileName) throws IOException {
//        File newFile = getTargetFile(moduleDirectory, fileName);
//        multipartFile.transferTo(newFile);
//        return Multimedia.MultimediaBuilder.anMultimedia()
//                .withContentType(multipartFile.getContentType())
//                .withFileDirectory(today)
//                .withFileName(fileName)
//                .withFileSize(multipartFile.getSize())
//                .withOriginalFileName(multipartFile.getOriginalFilename()).build();
//    }

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
//    public File getFileWithAbsolutePath(String moduleDirectory, String fileDirectory, String fileName) throws FileNotFoundException {
//        validateNotBlank(moduleDirectory, "moduleDirectory");
//        validateNotBlank(fileDirectory, "fileDirectory");
//        validateNotBlank(fileName, "fileName");
//
//        File file = new File(uploadedFilesBaseDirectory + moduleDirectory + fileDirectory + "/" + fileName);
//
//        if (!file.exists()) {
//            throw new FileNotFoundException("Multimedia File not found" + ", moduleDirectory: " + moduleDirectory +
//                    ", fileDirectory: " + fileDirectory + ", fileName: " + fileName);
//        }
//
//        return file;
//    }

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
//    private File getTargetFile(String moduleDirectory, String fileName) throws IOException {
//        validateNotBlank(moduleDirectory, "moduleDirectory");
//        validateNotBlank(fileName, "fileName");
//
//        String fullFileDirectory = uploadedFilesBaseDirectory + moduleDirectory + today + "/";
//
//        File filePath = new File(fullFileDirectory);
//
//        if (!filePath.exists()) {
//            FileUtils.forceMkdir(filePath);
//        }
//
//        return new File(filePath.getAbsolutePath() + "/" + fileName);
//    }
}
