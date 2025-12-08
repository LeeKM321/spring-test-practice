package com.codeit.library.service;

import com.codeit.library.domain.Member;
import com.codeit.library.dto.request.MemberCreateRequest;
import com.codeit.library.dto.response.MemberResponse;
import com.codeit.library.exception.DuplicateEmailException;
import com.codeit.library.exception.MemberNotFoundException;
import com.codeit.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }
        
        Member member = new Member(request.getName(), request.getEmail());
        Member saved = memberRepository.save(member);
        
        return MemberResponse.from(saved);
    }

    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new MemberNotFoundException(id));
        return MemberResponse.from(member);
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream()
            .map(MemberResponse::from)
            .collect(Collectors.toList());
    }

    public MemberResponse findByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new MemberNotFoundException("이메일 " + email + "인 회원을 찾을 수 없습니다"));
        return MemberResponse.from(member);
    }

    @Transactional
    public MemberResponse updateName(Long id, String name) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new MemberNotFoundException(id));
        
        member.updateName(name);
        
        return MemberResponse.from(member);
    }

    @Transactional
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new MemberNotFoundException(id);
        }
        memberRepository.deleteById(id);
    }
}

