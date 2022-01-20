package com.app.core.StudentInformationSystem.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true,updatable = false)
    private Long id;
    private String name;
    private String userName;
    private String password;
    private String confirmPassword;
    private String phone;
    private String imageUrl;
    private String email;
    private String Address;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Student(String name, String userName, String password, String confirmPassword, String phone, String imageUrl, String email, String address, Role role) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.email = email;
        Address = address;
        this.role = role;
    }

    public Student() {
        System.out.println("default consutructor student called");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", phone='" + phone + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", email='" + email + '\'' +
                ", Address='" + Address + '\'' +
                ", role=" + role +
                '}';
    }
//still not over-ridden the equal and hash code method.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id) && Objects.equals(name, student.name) && Objects.equals(userName, student.userName) && Objects.equals(password, student.password) && Objects.equals(confirmPassword, student.confirmPassword) && Objects.equals(phone, student.phone) && Objects.equals(imageUrl, student.imageUrl) && Objects.equals(email, student.email) && Objects.equals(Address, student.Address) && role == student.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userName, password, confirmPassword, phone, imageUrl, email, Address, role);
    }
}
