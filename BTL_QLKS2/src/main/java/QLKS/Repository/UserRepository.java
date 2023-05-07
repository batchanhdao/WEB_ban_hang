package QLKS.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import QLKS.Entity.*;

//UserRepository được sử dụng trong việc tương tác với cơ sở dữ liệu để lấy và lưu thông tin người dùng.
//kế thừa crudrepository thao tác CRUD (Create, Read, Update, Delete) với đối tượng User.

//CrudRepository<User, Long> UserRepository sẽ hoạt động với các đối tượng User và sử dụng kiểu dữ liệu Long 
//cho thuộc tính ID của User.

public interface UserRepository extends CrudRepository<User, Long>{

}