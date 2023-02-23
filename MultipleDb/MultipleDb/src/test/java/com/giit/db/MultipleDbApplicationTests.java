package com.giit.db;

import com.giit.db.mysql.entities.User;
import com.giit.db.mysql.repo.UserRepo;
import com.giit.db.postgres.entities.Product;
import com.giit.db.postgres.repo.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MultipleDbApplicationTests {

    @Autowired
	private UserRepo userRepo;
	@Autowired
	private ProductRepo productRepo;
	@Test
	public void dbTest() {
		User user=User.builder()
				.firstName("Sumit")
				.lastName("Singh")
				.email("sumit.com")
				.build();

		Product product=Product.builder()
				.name("Apple Iphone")
				.price(54000)
				.live(true)
				.description("This is Apple Product")
				.build();

		userRepo.save(user);
		productRepo.save(product);
		System.out.println("----------------------------------------------------");
		userRepo.findAll().forEach(e->System.out.println(e.getFirstName()));
		productRepo.findAll().forEach(e->System.out.println(e.getName()));
	}

}
