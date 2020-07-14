package com.pocketchat.utils.country_code;

import com.pocketchat.server.exceptions.country_code.CountryCodeNotFoundException;
import com.pocketchat.server.exceptions.general.StringEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class CountryCodeUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    Map<String, String> countries = new HashMap<>();
    Map<String, Locale> localeCountries = new HashMap<>();

    CountryCodeUtil() {
        initCountryList();
    }

    private void initCountryList() {
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("en", iso);
            countries.put(l.getDisplayCountry(), iso);
            localeCountries.put(iso, l);
        }
    }

    /**
     * Creates a Locale object.
     * Only accepts ISO-3166 Alpha-2 standard.
     */
    public Locale getLocaleByISOCountryCode(String isoCountryCode) {

        if (StringUtils.isEmpty(isoCountryCode)) {
          throw new StringEmptyException("ISO Country code is empty.");
        }

        Locale locale = localeCountries.get(isoCountryCode);

        if (ObjectUtils.isEmpty(locale)) {
            throw new CountryCodeNotFoundException("Unable to create country code with ISO country code: " + isoCountryCode);
        }

        return locale;
    }
}
