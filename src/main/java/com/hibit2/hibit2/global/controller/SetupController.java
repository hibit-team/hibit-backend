package com.hibit2.hibit2.global.controller;



import com.hibit2.hibit2.post.domain.DateTimeSlot;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.domain.TimeSlot;
import com.hibit2.hibit2.post.domain.What_do;
import com.hibit2.hibit2.post.repository.PostRepository;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/setup")
public class SetupController {
    private final PostRepository postRepository;
    private final UsersRepository usersRepository;


    @GetMapping("/")
    public  List<Post> setup() {
        Users user1 = new Users("a", "https://hibit2bucket.s3.ap-northeast-2.amazonaws.com/2.png");
        Users user2 = new Users("b", "https://hibit2bucket.s3.ap-northeast-2.amazonaws.com/2.png");
        Users user3 = new Users("c", "https://hibit2bucket.s3.ap-northeast-2.amazonaws.com/2.png");

        usersRepository.save(user1);
        usersRepository.save(user2);
        usersRepository.save(user3);
        List<Post> posts = createPosts(user1);


        postRepository.saveAll(posts);
        return posts;
    }
    private List<Post> createPosts(Users user1) {
        List<Post> posts = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 15; i++) {
            Post post = new Post();
            post.setUser(user1);
            post.setTitle("제목" + i);
            post.setContent("내용");
            post.setExhibition("에드워드 호퍼: 길 위에서");
            post.setStatus('N');
            int randomNumber = random.nextInt(4) + 2;
            post.setNumber(randomNumber);

            post.setOpenchat("http://kakao");

            List<What_do> whatDoOptions = new ArrayList<>(Arrays.asList(What_do.values()));
            int randomIndex = random.nextInt(whatDoOptions.size());
            What_do option = whatDoOptions.get(randomIndex);
            post.setWhat_do(option);

            int numSlots = random.nextInt(3) + 1;
            List<DateTimeSlot> dateTimeSlots = new ArrayList<>();
            for (int j = 0; j < numSlots; j++) {
                int month = random.nextInt(2) + 6;  // 6 or 7
                int day = random.nextInt(30) + 1;   // 1 to 30
                LocalDate date = LocalDate.of(2023, month, day);
                TimeSlot timeSlot = random.nextBoolean() ? TimeSlot.AM : TimeSlot.PM;
                dateTimeSlots.add(DateTimeSlot.builder()
                        .date(date)
                        .timeSlot(timeSlot)
                        .build());
            }
            post.setDateTimeSlots(dateTimeSlots);

            posts.add(post);
        }
        return posts;
    }
}
