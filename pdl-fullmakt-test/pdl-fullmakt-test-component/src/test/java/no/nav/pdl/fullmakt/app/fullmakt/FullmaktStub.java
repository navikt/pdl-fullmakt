
package no.nav.pdl.fullmakt.app.fullmakt;

import no.nav.pdl.fullmakt.app.fullmakt.FullmaktService;
import org.assertj.core.util.DateUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
//import no.nav.pdl.fullmakt.app.fullmakt.ListName;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FullmaktStub {
/*
    @Mock
    private FullmaktRepository repository;

    @InjectMocks
    private FullmaktService service;



    public  static void initializeFullmakt() {
        Fullmakt request = Fullmakt.builder()
                .ednretAv("")
                .endret(DateUtil.now())
                .fullmaktId(1L)
                .opphoert(false)
                .fullmaktsgiver("123")
                .fullmektig("321")
                .gyldigFraOgMed(DateUtil.now())
                .gyldigTilOgMed(DateUtil.now())
                .registrert(DateUtil.now())
                .registrertAv("123")
                .build();
        FullmaktService fullmaktService = new FullmaktService(new FullmaktRepository() {
            @Override
            public List<Fullmakt> findAllByFullmaktsgiver(String fullmaktsgiver) {
                return null;
            }

            @Override
            public List<Fullmakt> findAllByFullmektig(String fullmektig) {
                return null;
            }

            @Override
            public List<Fullmakt> findAll() {
                return null;
            }

            @Override
            public void deleteByFullmaktId(Long fullmaktId) {

            }

            @Override
            public Optional<Fullmakt> findByFullmaktId(Long fullmaktId) {
                return Optional.empty();
            }

            @Override
            public List<Fullmakt> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Fullmakt> findAllById(Iterable<Integer> iterable) {
                return null;
            }

            @Override
            public <S extends Fullmakt> List<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Fullmakt> S saveAndFlush(S s) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<Fullmakt> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Fullmakt getOne(Integer integer) {
                return null;
            }

            @Override
            public <S extends Fullmakt> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Fullmakt> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Fullmakt> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Fullmakt> S save(S s) {
                return null;
            }

            @Override
            public Optional<Fullmakt> findById(Integer integer) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Integer integer) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Integer integer) {

            }

            @Override
            public void delete(Fullmakt fullmakt) {

            }

            @Override
            public void deleteAll(Iterable<? extends Fullmakt> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Fullmakt> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Fullmakt> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Fullmakt> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Fullmakt> boolean exists(Example<S> example) {
                return false;
            }
        });
        fullmaktService.save(request);

    }

*/

}

