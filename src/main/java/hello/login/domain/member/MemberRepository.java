package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save member = {}", member);
        store.put(member.getId(), member); //왜 있어야하지? 아하 findById 에서 사용하기 위해 넣어놓는거군!
        return member;
    }

    public Member findById(Long id) {
        return store.get(id); //Map이므로 key인 id넣어주면 value 인 Member 가 나옴.
    }

    public Optional<Member> findByLongId(String loginId) { //못찾을 수도 있으니까 Optional 사용!
//        List<Member> all = findAll();
//        for (Member m : all) {
//            if (m.getLoginId().equals(loginId)) {
//                return Optional.of(m);
//            }
//        }
//        return Optional.empty();
        //list를 stream으로 바꾼다. 마치 for문 돌린다고 보면 된다.
        //filter하면 (가져온 데이터를 m이라고 하고, -> 조건에 맞는얘들만 다음조건(findFirst)으로 넘어갈수있다.)
        return findAll().stream()
                .filter(m->m.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() { //List는 도형이라면 ArrayList는 정사각형!
        return new ArrayList<>(store.values()); //key빼고 value 만 받아서 리스트로 뺐음!
    }

    public void clearStore() {
        store.clear();
    }
}
