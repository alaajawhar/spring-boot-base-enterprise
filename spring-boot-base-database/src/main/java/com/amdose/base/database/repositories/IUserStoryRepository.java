package com.amdose.base.database.repositories;

import com.amdose.base.database.entities.UserStory;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Alaa Jawhar
 */
public interface IUserStoryRepository extends CrudRepository<UserStory, Long> {
}
