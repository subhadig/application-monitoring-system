package org.subhadig.ams.datacollectionservice.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
