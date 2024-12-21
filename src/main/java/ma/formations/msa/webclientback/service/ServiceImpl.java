package ma.formations.msa.webclientback.service;

import lombok.AllArgsConstructor;
import ma.formations.msa.webclientback.dao.EmpRepository;
import ma.formations.msa.webclientback.dtos.EmpDto;
import ma.formations.msa.webclientback.model.Emp;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Optional;
@Service
@AllArgsConstructor
public class ServiceImpl implements IService {
    private EmpRepository empRepository;
    private ModelMapper modelMapper;
    public Flux<EmpDto> findAll() {
        return empRepository.findAll().map(bo -> modelMapper.map(bo, EmpDto.class));
    }
    public Flux<EmpDto> findByNameContaining(String name) {
        return empRepository.findByNameContaining(name).map(bo -> modelMapper.map(bo,
                EmpDto.class));
    }
    public Mono<EmpDto> findById(Long id) {
        return empRepository.findById(id).map(bo -> modelMapper.map(bo, EmpDto.class));
    }
    @Transactional
    public Mono<EmpDto> save(EmpDto dto) {
        return empRepository.save(modelMapper.map(dto, Emp.class)).
                map(bo -> modelMapper.map(bo, EmpDto.class));
    }
    @Transactional
    public Mono<EmpDto> update(Long id, EmpDto dto) {
        return empRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(optionalEmp -> {
                    if (optionalEmp.isPresent()) {
                        dto.setId(id);
                        return empRepository.save(modelMapper.map(dto, Emp.class)).
                                map(bo -> modelMapper.map(bo, EmpDto.class));
                    }
                    return Mono.empty();
                });
    }
    @Transactional
    public Mono<Void> deleteById(Long id) {
        return empRepository.deleteById(id);
    }
    @Transactional
    public Mono<Void> deleteAll() {
        return empRepository.deleteAll();
    }
    @Override
    public Flux<EmpDto> findBySalaireBetween(Double min, Double max) {
        return empRepository.findBySalaireBetween(min, max).map(bo -> modelMapper.map(bo,
                EmpDto.class));
    }
}