/*
 * Copyright 2023 Szabolcs Bazil Papp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.aestallon.giannitsa.app.auth;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  @Query("select * from account_user where username = :username")
  Optional<User> findOptionalByUsername(String username);

  @Query("select * from account_user where reg_token = :registrationToken")
  Optional<User> findOptionalByToken(UUID registrationToken);

  boolean existsByUsername(String username);

  @Modifying
  @Query("update account_user u set u.inactive = false where u.id = :id")
  boolean activate(Long id);
}
