package com.hibit2.hibit2.declaration.service;


import com.hibit2.hibit2.comment.domain.Comment;
import com.hibit2.hibit2.comment.repository.CommentRepository;
import com.hibit2.hibit2.declaration.domain.Declaration;
import com.hibit2.hibit2.declaration.dto.DeclarationSaveDto;
import com.hibit2.hibit2.declaration.repository.DeclarationRepository;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.repository.PostRepository;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeclarationService {
    private final DeclarationRepository declarationRepository;
    private final PostRepository postRepository;
    private final UsersRepository usersRepository;
    private final CommentRepository commentRepository;

    public Declaration createDeclaration(DeclarationSaveDto declarationSaveDto) {

        Users user = usersRepository.findById(declarationSaveDto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Users repostUser = usersRepository.findById(declarationSaveDto.getReportId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Post post = null;
        if (declarationSaveDto.getPostIdx() != null) {
            post = postRepository.findById(declarationSaveDto.getPostIdx())
                    .orElse(null);
        }

        Comment comment = null;
        if (declarationSaveDto.getCommentIdx() != null) {
            comment = commentRepository.findById(declarationSaveDto.getCommentIdx())
                    .orElse(null);
        }


        // Declaration 객체를 생성하여 리턴
        Declaration declaration = new Declaration();
        declaration.setDeclarationType(declarationSaveDto.getDeclarationType());
        declaration.setContent(declarationSaveDto.getContent());

        // postId, commentId 등의 정보를 이용하여 Declaration 객체에 필요한 설정 수행
        declaration.setUser(user);
        declaration.setReportUser(repostUser);
        declaration.setPost(post);
        declaration.setComment(comment);

        return declarationRepository.save(declaration);
    }


}
