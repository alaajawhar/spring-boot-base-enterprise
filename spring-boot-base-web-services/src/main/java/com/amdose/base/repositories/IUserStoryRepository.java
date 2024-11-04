package com.amdose.base.repositories;

import com.amdose.base.repositories.entities.UserStory;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Alaa Jawhar
 */
public interface IUserStoryRepository extends CrudRepository<UserStory, Long> {
}
