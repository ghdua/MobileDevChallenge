package com.example.mobiledevchallenge.Tools;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Utils {
    private static List<String> countryCodes;
    public static List<String> LoadCountryCodes() {
        Set<String> set = PhoneNumberUtil.getInstance().getSupportedRegions();
        String[] arr = set.toArray(new String[set.size()]);
        countryCodes = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            String a = arr[i];
            int ss = PhoneNumberUtil.getInstance().getCountryCodeForRegion(a);
            countryCodes.add(a + " +" + String.valueOf(ss));
        }
        Collections.sort(countryCodes);
        countryCodes.add(0, "Código");
        return countryCodes;
    }
}
