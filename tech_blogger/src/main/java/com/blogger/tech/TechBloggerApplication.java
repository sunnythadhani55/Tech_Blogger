package com.blogger.tech;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.blogger.tech.model.Tag;
import com.blogger.tech.repository.TagRepository;

@SpringBootApplication
public class TechBloggerApplication {

  public static void main(String[] args) {
    SpringApplication.run(TechBloggerApplication.class, args);
  }

}


@Component
class CommndLineRunner implements CommandLineRunner {

  @Autowired
  TagRepository tagRepository;

  @Override
  public void run(String... args) throws Exception {

    List<Tag> tagList = new ArrayList<>();
    tagList.add(new Tag(null, "Youtube", LocalDateTime.now(), LocalDateTime.now(), null, null));
    tagList.add(new Tag(null, "Twitter", LocalDateTime.now(), LocalDateTime.now(), null, null));
    tagList.add(new Tag(null, "Blockchain", LocalDateTime.now(), LocalDateTime.now(), null, null));
    tagList.add(new Tag(null, "IOT", LocalDateTime.now(), LocalDateTime.now(), null, null));
    tagList.add(new Tag(null, "AI", LocalDateTime.now(), LocalDateTime.now(), null, null));
    tagList.add(new Tag(null, "Qualcome", LocalDateTime.now(), LocalDateTime.now(), null, null));
    tagList.add(new Tag(null, "Smart Watch", LocalDateTime.now(), LocalDateTime.now(), null, null));
    tagList.add(new Tag(null, "Algorithm", LocalDateTime.now(), LocalDateTime.now(), null, null));
    tagList.add(
        new Tag(null, "Web Development", LocalDateTime.now(), LocalDateTime.now(), null, null));

    tagRepository.saveAll(tagList);

  }

}
