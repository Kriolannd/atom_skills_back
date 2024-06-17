package ru.atomskills.back.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.atomskills.back.models.SomeEntity;
import ru.atomskills.back.services.SomeEntityService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("entities")
@RequiredArgsConstructor
public class SomeEntityController {

    private final SomeEntityService someEntityService;

    @GetMapping
    public ResponseEntity<List<SomeEntity>> findAll() {
        return ResponseEntity.ok(someEntityService.findAll());
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<SomeEntity>> findPage(Pageable pageable,
                                                     @RequestParam(value = "textQuery", required = false) String textQuery,
                                                     @RequestParam(value = "fromNumber", required = false) Integer fromNumber,
                                                     @RequestParam(value = "toNumber", required = false) Integer toNumber,
                                                     @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
                                                     @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
                                                     @RequestParam(value = "enums", required = false) List<SomeEntity.SomeEnum> enums) {
        return ResponseEntity.ok(someEntityService.findPage(pageable, textQuery, fromNumber, toNumber, fromDate, toDate, enums));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SomeEntity> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(someEntityService.findById(id));
    }

    @GetMapping(path = "/{id}/file", produces = "application/octet-stream")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Integer id) {
        Resource resource = someEntityService.downloadFile(id);
        String contentDisposition = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @PostMapping
    public ResponseEntity<SomeEntity> save(@RequestBody SomeEntity someEntity) {
        SomeEntity saved = someEntityService.save(someEntity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @PostMapping(value = "/form", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SomeEntity> save(@RequestParam("text") String text,
                                           @RequestParam("number") Integer number,
                                           @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                           @RequestParam("enum") SomeEntity.SomeEnum someEnum,
                                           @RequestPart("file") MultipartFile multipartFile) {
        SomeEntity saved = someEntityService.save(text, number, date, someEnum, multipartFile);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Integer id, @RequestBody SomeEntity someEntity) {
        someEntityService.update(id, someEntity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        someEntityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
