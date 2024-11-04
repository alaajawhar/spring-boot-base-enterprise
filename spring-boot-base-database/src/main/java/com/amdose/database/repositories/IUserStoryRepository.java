package com.amdose.database.repositories;

import com.amdose.database.entities.UserStory;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Alaa Jawhar
 */
public interface IUserStoryRepository extends CrudRepository<UserStory, Long> {
}
