package cz.ivosahlik.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Elektro implements Serializable {

	Long id;
	String name;

}
