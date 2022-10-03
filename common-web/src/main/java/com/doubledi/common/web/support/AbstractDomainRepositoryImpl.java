package com.doubledi.common.web.support;

import com.doubledi.common.model.mapper.EntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class AbstractDomainRepositoryImpl<D, E, I> implements DomainRepository<D, I> {

    private final JpaRepository<E, I> jpaRepository;
    private final EntityMapper<D, E> entityMapper;


    @Override
    public Optional<D> findById(I id) {
        return this.jpaRepository.findById(id).map(this.entityMapper::toDomain).map(this::enrich);
    }

    @Override
    public List<D> findAllByIds(List<I> ids) {
        return this.jpaRepository.findAllById(ids).stream()
                .map(this.entityMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public D save(D domain) {
        E entity = entityMapper.toEntity(domain);
        this.jpaRepository.save(entity);
        return domain;
    }

    @Override
    public List<D> saveAll(List<D> domains) {
        List<E> entities = this.entityMapper.toEntity(domains);
        this.jpaRepository.saveAll(entities);
        return domains;
    }

    @Override
    public D getById(I id) {
        return null;
    }

    protected D enrich(D d) {
        List<D> list = List.of(d);
        return list.get(0);
    }

    protected List<D> enrich(List<D> list) {
        return list;
    }
}
