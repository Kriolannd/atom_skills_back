package ru.atomskills.back.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.atomskills.back.models.SomeEntity;
import ru.atomskills.back.repositories.SomeEntityRepository;
import ru.atomskills.back.repositories.specifications.SomeEntityRepositorySpecification;
import ru.atomskills.back.utils.FilesUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SomeEntityService {

    private final SomeEntityRepository someEntityRepository;

    public List<SomeEntity> findAll() {
        return someEntityRepository.findAll();
    }

    public Page<SomeEntity> findPage(Pageable pageable, String textQuery, Integer fromNumber, Integer toNumber,
                                     LocalDateTime fromDate, LocalDateTime toDate, List<SomeEntity.SomeEnum> enums) {
        return someEntityRepository.findAll(
                SomeEntityRepositorySpecification.filter(textQuery, fromNumber, toNumber, fromDate, toDate, enums),
                pageable
        );
    }

    public SomeEntity findById(Integer id) {
        return someEntityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public SomeEntity save(SomeEntity someEntity) {
        return someEntityRepository.save(someEntity);
    }

    @SneakyThrows
    @Transactional
    public SomeEntity save(String text, Integer number, LocalDateTime date, SomeEntity.SomeEnum someEnum, MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        SomeEntity someEntity = someEntityRepository.save(
                SomeEntity.builder()
                        .text(text)
                        .number(number)
                        .date(date)
                        .someEnum(someEnum)
                        .file(fileName)
                        .build()
        );
        FilesUtil.saveFile(String.valueOf(someEntity.getId()), fileName, multipartFile);
        return someEntity;
    }

    public Resource downloadFile(Integer id) {
        return FilesUtil.getFile(String.valueOf(id), someEntityRepository.findById(id).orElseThrow().getFile());
    }

    @Transactional
    public void update(Integer id, SomeEntity someEntity) {
        if (!someEntityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        someEntity.setId(id);
        someEntityRepository.save(someEntity);
    }

    @Transactional
    public void delete(Integer id) {
        someEntityRepository.deleteById(id);
    }
}
