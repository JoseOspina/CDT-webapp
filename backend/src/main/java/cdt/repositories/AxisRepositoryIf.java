package cdt.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import cdt.entities.Axis;

public interface AxisRepositoryIf extends CrudRepository<Axis, UUID> {
	
}
