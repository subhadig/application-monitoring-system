package org.subhadig.ams.datacollectionservice.config.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.subhadig.ams.datacollectionservice.config.DataCollectionConfig;
import org.subhadig.ams.datacollectionservice.config.repository.DataCollectionConfigRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * This class provides the REST APIs
 * for {@link DataCollectionConfig}
 * @author subhadig@github
 */
@RestController
@RequestMapping("data-collection-configs")
@Api("DataCollectionConfiguration resources")
public class DataCollectionConfigController {
    
    @Autowired
    private DataCollectionConfigRepository configRepository;

    @GetMapping("")
    @ApiOperation("Get all DataCollectionConfigurations")
    public List<DataCollectionConfig> getAllConfigs() {
        return configRepository.findAll();
    }
    
    @GetMapping("/{id}")
    @ApiOperation("Get one DataCollectionConfiguration")
    public DataCollectionConfig getConfig(@PathVariable("id") String id) {
        Optional<DataCollectionConfig> fetchedConfig = configRepository.findById(id);
        return fetchedConfig.orElseThrow(
                () -> new DataCollectionConfigNotFoundException(
                        String.format("Config with id %s is not available", id)));
    }
    
    @PostMapping()
    @ApiOperation("Create new DataCollectionConfiguration")
    public ResponseEntity<String> createConfig(@RequestBody DataCollectionConfig config) {
        DataCollectionConfig savedConfig = configRepository.save(config);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path("/{id}")
                                                  .buildAndExpand(savedConfig.getId())
                                                  .toUri();
        return ResponseEntity.created(location).build();
    }
    
    @DeleteMapping("/{id}")
    @ApiOperation("Delete a DataCollectionConfiguration")
    public ResponseEntity<DataCollectionConfig> deleteConfig(@PathVariable("id") String id) {
        if(id == null) {
            return ResponseEntity.badRequest().build();
        }
        configRepository.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
