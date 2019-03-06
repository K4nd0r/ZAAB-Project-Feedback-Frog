package com.fedex.feedbackfrog.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User {
  @Id
  @GeneratedValue
  private long id;
  private String name;
  private boolean isAdmin;

  @OneToMany(mappedBy = "reviewer")
  private List<Review> sentReviews;

  public User() {}

  public User(String name, boolean isAdmin, List<Review> sentReviews) {
    this.name = name;
    this.isAdmin = isAdmin;
    this.sentReviews = sentReviews;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  public List<Review> getSentReviews() {
    return sentReviews;
  }

  public void setSentReviews(List<Review> sentReviews) {
    this.sentReviews = sentReviews;
  }
}
