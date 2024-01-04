package com.fiek.helloworldfiek_2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UniversityGson {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("alpha_two_code")
    @Expose
    private String alphaTwoCode;
    @SerializedName("domains")
    @Expose
    private List<String> domains;
    @SerializedName("web_pages")
    @Expose
    private List<String> webPages;
    @SerializedName("state-province")
    @Expose
    private Object stateProvince;

    /**
     * No args constructor for use in serialization
     *
     */
    public UniversityGson() {
    }

    /**
     *
     * @param country
     * @param webPages
     * @param name
     * @param domains
     * @param stateProvince
     * @param alphaTwoCode
     */
    public UniversityGson(String name, String country, String alphaTwoCode, List<String> domains, List<String> webPages, Object stateProvince) {
        super();
        this.name = name;
        this.country = country;
        this.alphaTwoCode = alphaTwoCode;
        this.domains = domains;
        this.webPages = webPages;
        this.stateProvince = stateProvince;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAlphaTwoCode() {
        return alphaTwoCode;
    }

    public void setAlphaTwoCode(String alphaTwoCode) {
        this.alphaTwoCode = alphaTwoCode;
    }

    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public List<String> getWebPages() {
        return webPages;
    }

    public void setWebPages(List<String> webPages) {
        this.webPages = webPages;
    }

    public Object getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(Object stateProvince) {
        this.stateProvince = stateProvince;
    }

}