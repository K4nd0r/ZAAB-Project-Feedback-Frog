package com.fedex.feedbackfrog.service;

import com.fedex.feedbackfrog.model.dto.ReviewDTO;
import com.fedex.feedbackfrog.model.dto.ReviewDTO_Post;
import com.fedex.feedbackfrog.model.entity.Review;
import com.fedex.feedbackfrog.repository.MentorRepository;
import com.fedex.feedbackfrog.repository.ReviewRepository;
import com.fedex.feedbackfrog.repository.UserRepository;
import com.fedex.feedbackfrog.service.serviceInterface.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements CreateService<ReviewDTO_Post>,
                                          ReadService<ReviewDTO>,
                                          UpdateService<ReviewDTO>,
                                          DeleteService<ReviewDTO> {

  private ReviewRepository repository;
  private UserRepository userRepository;
  private MentorRepository mentorRepository;
  private ModelMapper mapper;

  @Autowired
  public ReviewServiceImpl(ReviewRepository repository, ModelMapper mapper, UserRepository userRepository, MentorRepository mentorRepository) {
    this.repository = repository;
    this.mapper = mapper;
    this.userRepository = userRepository;
    this.mentorRepository = mentorRepository;
  }

  @Override
  public void save(ReviewDTO_Post dto) {
    if (dto != null){
      Review review = mapper.map(dto, Review.class);
      review.setReviewer(userRepository.findUserByName(dto.getReviewer().getName()));
      review.setMentor(mentorRepository.findByName(dto.getMentor().getName()));
      review.getMentor().setPoints(dto.rating.toString().equals("PLUS") ? 1 : -1);
      repository.save(review);
    }
  }

  @Override
  public List<ReviewDTO> getAll() {
    List<ReviewDTO> reviewDTOList = new ArrayList<>();
    List<Review> reviewList = new ArrayList<>();
    repository.findAll().forEach(reviewList::add);

    for (Review review : reviewList) {
      reviewDTOList.add(mapper.map(review, ReviewDTO.class));
    }

    return reviewDTOList;
  }

  @Override
  public List<ReviewDTO> getByTextContaining(String text) {
    List<ReviewDTO> reviewDTOList = new ArrayList<>();
    List<Review> reviewList = repository.getByTextContaining(text);
    for (Review review : reviewList) {
      reviewDTOList.add(mapper.map(review, ReviewDTO.class));
    }
    return reviewDTOList;
  }

  @Override
  public ReviewDTO getById(long id) {
    return mapper.map(repository.findById(id).orElse(null), ReviewDTO.class);
  }

  @Override
  public boolean existsByText(String text) {
    return repository.existsByTextContaining(text);
  }

  @Override
  public boolean existsById(long id) {
    return repository.existsById(id);
  }

  @Override
  public void editById(long id, ReviewDTO dto) {
    Review review = repository.findById(id).orElse(null);
    mapper.map(dto, review);
    repository.save(review);
  }

  @Override
  public void deleteById(long id) {
    repository.findById(id).get().setMentor(null);
    repository.findById(id).get().setReviewer(null);
    repository.deleteById(id);
  }

  @Override
  public boolean existsByName(String name) {
    return false;
  }

  @Override
  public ReviewDTO getByName(String name) {
    return null;
  }
}
