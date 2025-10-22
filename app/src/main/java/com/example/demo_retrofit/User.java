package com.example.demo_retrofit;

public class User {
    private int id;
    private String name;
    private String email;
    private String username;
    private String phone;
    private String website;
    private Address address;
    private Company company;

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPhone() { return phone; }
    public String getWebsite() { return website; }
    public Address getAddress() { return address; }
    public Company getCompany() { return company; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username) { this.username = username; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setWebsite(String website) { this.website = website; }
    public void setAddress(Address address) { this.address = address; }
    public void setCompany(Company company) { this.company = company; }
}

class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;

    public String getStreet() { return street; }
    public String getSuite() { return suite; }
    public String getCity() { return city; }
    public String getZipcode() { return zipcode; }
    public Geo getGeo() { return geo; }

    public void setStreet(String street) { this.street = street; }
    public void setSuite(String suite) { this.suite = suite; }
    public void setCity(String city) { this.city = city; }
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }
    public void setGeo(Geo geo) { this.geo = geo; }
}

class Geo {
    private String lat;
    private String lng;

    public String getLat() { return lat; }
    public String getLng() { return lng; }

    public void setLat(String lat) { this.lat = lat; }
    public void setLng(String lng) { this.lng = lng; }
}

class Company {
    private String name;
    private String catchPhrase;
    private String bs;

    public String getName() { return name; }
    public String getCatchPhrase() { return catchPhrase; }
    public String getBs() { return bs; }

    public void setName(String name) { this.name = name; }
    public void setCatchPhrase(String catchPhrase) { this.catchPhrase = catchPhrase; }
    public void setBs(String bs) { this.bs = bs; }
}