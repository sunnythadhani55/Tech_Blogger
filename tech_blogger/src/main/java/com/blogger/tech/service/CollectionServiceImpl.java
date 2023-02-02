package com.blogger.tech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.blogger.tech.dto.ArticleDTO;
import com.blogger.tech.dto.CollectionDTO;
import com.blogger.tech.exception.ResourceNotFoundException;
import com.blogger.tech.exception.UnauthorizedException;
import com.blogger.tech.model.Article;
import com.blogger.tech.model.Collection;
import com.blogger.tech.model.User;
import com.blogger.tech.repository.ArticleRepository;
import com.blogger.tech.repository.CollectionRepository;
import com.blogger.tech.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CollectionServiceImpl implements CollectionService {

  private final CollectionRepository collectionRepository;

  private final UserRepository userRepository;

  private final ArticleRepository articleRepository;

  @Override
  public List<CollectionDTO> getAllByUserId(Long userId) {

    Optional<User> user = userRepository.findById(userId);

    return collectionRepository.findByUser(user.get()).stream()
        .map(collection -> new CollectionDTO(collection, false, false))
        .collect(Collectors.toList());
  }

  @Override
  public CollectionDTO getById(Long collectionId, Long userId) {

    Optional<User> user = userRepository.findById(userId);

    if (!collectionRepository.existsById(collectionId))
      throw new ResourceNotFoundException("Collection", "Id", collectionId.toString());

    List<Collection> collectionList = collectionRepository.findByUser(user.get());

    for (Collection collection : collectionList) {
      if (collectionId == collection.getId()) {
        return new CollectionDTO(collection, true, true);
      }
    }

    throw new UnauthorizedException("Collection", "Id", collectionId.toString());
  }

  @Override
  public CollectionDTO add(CollectionDTO collectionDTO, Long userId) {

    Collection collection = new Collection(collectionDTO);

    Optional<User> user = userRepository.findById(userId);
    collection.setUser(user.get());

    List<Article> articles = new ArrayList();

    List<ArticleDTO> articleDTOList = collectionDTO.getArticleDTOList();

    for (ArticleDTO articleDTO : articleDTOList) {
      Long articleId = articleDTO.getId();

      Article article = articleRepository.findById(articleId)
          .orElseThrow(() -> new ResourceNotFoundException("Article", "Id", articleId.toString()));

      articles.add(article);
    }

    collection.setArticleList(articles);

    collection=collectionRepository.save(collection);

    return new CollectionDTO(collection, true, true);
  }

  @Override
  public CollectionDTO update(CollectionDTO collectionDTO, Long userId) {
    Collection collection = collectionRepository.findById(collectionDTO.getId()).orElseThrow(
        () -> new ResourceNotFoundException("Collection", "Id", collectionDTO.getId().toString()));

    collectionDTO.setCreatedAt(collection.getCreatedAt());
    collection = new Collection(collectionDTO);

    Optional<User> user = userRepository.findById(userId);
    collection.setUser(user.get());

    List<ArticleDTO> articleDTOList = collectionDTO.getArticleDTOList();

    List<Article> articles = new ArrayList();

    for (ArticleDTO articleDTO : articleDTOList) {
      Long articleId = articleDTO.getId();

      Article article = articleRepository.findById(articleId)
          .orElseThrow(() -> new ResourceNotFoundException("Article", "Id", articleId.toString()));

      articles.add(article);
    }

    collection.setArticleList(articles);

    collection=collectionRepository.save(collection);

    return new CollectionDTO(collection, true, true);
  }

  @Override
  public void deleteById(Long collectionId, Long userId) {
    Optional<User> user = userRepository.findById(userId);

    List<Collection> collectionList = collectionRepository.findByUser(user.get());

    for (Collection collection : collectionList) {
      if (collectionId == collection.getId()) {
        collectionRepository.deleteById(collectionId);
        return;
      }
    }

    throw new UnauthorizedException("Collection", "Id", collectionId.toString());
  }

}
