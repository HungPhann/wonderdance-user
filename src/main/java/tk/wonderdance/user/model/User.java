package tk.wonderdance.user.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "user_user")
public class User extends DateAudit{
    @Id
    private Long id;

    @Column(name = "email")
    @NotNull
    @NotBlank
    private String email;

    @Column(name = "first_name")
    @NotNull
    @NotBlank
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @NotBlank
    private String lastName;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private char gender = 'U';

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "description")
    private String description;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_achievements",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "achievement_id"))
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Achievement> achievements = new HashSet<>();

    @Column(name = "address_name")
    private String addressName;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "zipcode", length = 20)
    private String zipCode;

    @Column(name = "dance_genre")
    private DanceGenreName danceGenre;

    @Column(name = "dance_crew")
    private String danceCrew;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<User> followers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_followings",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private Set<User> followings = new HashSet<>();

    public User(Long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
    }

    public Map<String, Object> getInformation(List<String> attributes){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_id", id);

        for(String attribute : attributes){
            switch (attribute){
                case "email":
                    map.put("email", email);
                    break;

                case "first_name":
                    map.put("first_name", firstName);
                    break;

                case "last_name":
                    map.put("last_name", lastName);
                    break;

                case "nick_name":
                    map.put("nick_name", nickName);
                    break;

                case "description":
                    map.put("description", description);
                    break;

                case "dob":
                    try {
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        map.put("dob", dateFormat.format(dob));
                    } catch (NullPointerException e){
                        map.put("dob", dob);
                    }

                    break;

                case "gender":
                    map.put("gender", gender);
                    break;

                case "profile_picture":
                    map.put("profile_picture", profilePicture);
                    break;

                case "achievements":
                    map.put("achievements", getAchievementJson(achievements));
                    break;

                case "address_name":
                    map.put("address_name", addressName);
                    break;

                case "street":
                    map.put("street", street);
                    break;

                case "city":
                    map.put("city", city);
                    break;

                case "state":
                    map.put("state", state);
                    break;

                case "country":
                    map.put("country", country);
                    break;

                case "zip_code":
                    map.put("zip_code", zipCode);
                    break;

                case "dance_genre":
                    map.put("dance_genre", danceGenre);
                    break;

                case "dance_crew":
                    map.put("dance_crew", danceCrew);
                    break;

                case "followers":
                    map.put("followers", getUserIds(followers));
                    break;

                case "number_of_followers":
                    map.put("number_of_followers", followers.size());
                    break;

                case "followings":
                    map.put("followings", getUserIds(followings));
                    break;

                case "number_of_followings":
                    map.put("number_of_followings", followings.size());
                    break;

                default:
                    break;
            }

        }

        return map;
    }

    public void updateInformation(Map<String, Object> data) throws Exception{
        for(String key : data.keySet()){
            switch (key){
                case "first_name":
                    this.firstName = (String) data.get(key);
                    break;

                case "last_name":
                    this.lastName = (String) data.get(key);
                    break;

                case "nick_name":
                    this.nickName = (String) data.get(key);
                    break;

                case "description":
                    this.description = (String) data.get(key);
                    break;

                case "dob":
                    try {
                        this.dob = new SimpleDateFormat("dd/MM/yyyy").parse((String) data.get(key));
                    }
                    catch (ParseException e){}
                    break;

                case "gender":
                    this.gender = (char) data.get(key);
                    break;

                case "address_name":
                    this.addressName = (String) data.get(key);
                    break;

                case "street":
                    this.street = (String) data.get(key);
                    break;

                case "city":
                    this.city = (String) data.get(key);
                    break;

                case "state":
                    this.state = (String) data.get(key);
                    break;

                case "country":
                    this.country = (String) data.get(key);
                    break;

                case "zip_code":
                    this.zipCode = (String) data.get(key);
                    break;

                case "dance_genre":
                    this.danceGenre = DanceGenreName.valueOf((String) data.get(key));
                    break;

                case "dance_crew":
                    this.danceCrew = (String) data.get(key);
                    break;

                default:
                    break;
          }
        }
    }

    public static Set<Long> getUserIds(Set<User> users){
        Set<Long> userIDs = new HashSet<>();

        for (User user : users){
            userIDs.add(user.getId());
        }
        return userIDs;
    }


    public static Set<Map<String, Object>> getAchievementJson(Set<Achievement> achievements){
        Set<Map<String, Object>> set = new HashSet<>();

        for (Achievement achievement : achievements){
            set.add(achievement.toJson());
        }
        return set;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Set<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(Set<Achievement> achievements) {
        this.achievements = achievements;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public DanceGenreName getDanceGenre() {
        return danceGenre;
    }

    public void setDanceGenre(DanceGenreName danceGenre) {
        this.danceGenre = danceGenre;
    }

    public String getDanceCrew() {
        return danceCrew;
    }

    public void setDanceCrew(String danceCrew) {
        this.danceCrew = danceCrew;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<User> getFollowings() {
        return followings;
    }

    public void setFollowings(Set<User> followings) {
        this.followings = followings;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
}
