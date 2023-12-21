package com.hibit2.hibit2.declaration.service;


import com.hibit2.hibit2.comment.domain.Comment;
import com.hibit2.hibit2.comment.repository.CommentRepository;
import com.hibit2.hibit2.declaration.domain.Declaration;
import com.hibit2.hibit2.declaration.dto.DeclarationSaveDto;
import com.hibit2.hibit2.declaration.repository.DeclarationRepository;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.exception.NotFoundMemberException;
import com.hibit2.hibit2.member.repository.MemberRepository;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DeclarationService {
    private final DeclarationRepository declarationRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public Declaration createDeclaration(DeclarationSaveDto declarationSaveDto) {
        Declaration declaration = new Declaration();

        Member member= memberRepository.findByNickname(declarationSaveDto.getUserId())
                .orElseThrow(() -> new NotFoundMemberException());

        Post post = null;
        if (declarationSaveDto.getPostIdx() != null) {
            post = postRepository.findById(declarationSaveDto.getPostIdx())
                    .orElse(null);
            Member reportMember= post.getMember();
            declaration.setReport(reportMember);
        }

        Comment comment = null;
        if (declarationSaveDto.getCommentIdx() != null) {
            comment = commentRepository.findById(declarationSaveDto.getCommentIdx())
                    .orElse(null);
            Member reportMember= comment.getMember();
            declaration.setReport(reportMember);
        }


        // Declaration 객체를 생성하여 리턴
        declaration.setDeclarationType(declarationSaveDto.getDeclarationType());
        declaration.setContent(declarationSaveDto.getContent());

        // postId, commentId 등의 정보를 이용하여 Declaration 객체에 필요한 설정 수행
        declaration.setMember(member);
        declaration.setPost(post);
        declaration.setComment(comment);

        return declarationRepository.save(declaration);
    }

}
