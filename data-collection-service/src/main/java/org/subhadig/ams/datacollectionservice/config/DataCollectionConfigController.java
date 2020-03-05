package org.subhadig.ams.datacollectionservice.config;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class provides the REST APIs
 * for {@link DataCollectionConfig}
 * @author subhadip
 */
@RestController
@RequestMapping("data-collection-configs")
public class DataCollectionConfigController {
	
	@Autowired
	private DataCollectionConfigRepository configRepository;

	@GetMapping("")
	public List<DataCollectionConfig> getAllConfigs() {
		return configRepository.findAll();
	}
	
	@GetMapping("{id}")
	public DataCollectionConfig getConfig(String id) {
		Optional<DataCollectionConfig> fetchedConfig = configRepository.findById(id);
		return fetchedConfig.orElseThrow(
				() -> new DataCollectionConfigNotFoundException(id + " is not available"));
	}
	
	@PostMapping()
	public ResponseEntity<String> createConfig(@RequestBody DataCollectionConfig config) {
		DataCollectionConfig savedConfig = configRepository.save(config);
		return ResponseEntity.created(URI.create("/" + savedConfig.getId())).build();
		//TODO: Need to work around Spring security csrf and Swagger UI problem with POST
	}
}
