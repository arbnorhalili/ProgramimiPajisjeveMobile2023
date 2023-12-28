package com.fiek.helloworldfiek_2;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class University {
    private String Name;
    private List<String> WebPages;
    private String Country;
    private String AlphaTwoCode;
    private List<String> Domains;

    public University(String name,
                      JSONArray webPages,
                      String country,
                      String alphaTwoCode,
                      JSONArray domains) {
        Name = name;
        Country = country;
        AlphaTwoCode = alphaTwoCode;

        WebPages = new ArrayList<>();
        for(int i=0;i<webPages.length();i++)
        {
            try {
                WebPages.add(webPages.getString(i));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        Domains = new ArrayList<>();
        for(int i=0;i<domains.length();i++)
        {
            try {
                Domains.add(domains.getString(i));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<String> getWebPages() {
        return WebPages;
    }

    public void setWebPages(List<String> webPages) {
        WebPages = webPages;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAlphaTwoCode() {
        return AlphaTwoCode;
    }

    public void setAlphaTwoCode(String alphaTwoCode) {
        AlphaTwoCode = alphaTwoCode;
    }

    public List<String> getDomains() {
        return Domains;
    }

    public void setDomains(List<String> domains) {
        Domains = domains;
    }
}
