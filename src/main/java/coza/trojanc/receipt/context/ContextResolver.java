package coza.trojanc.receipt.context;

import java.util.Map;

/**
 * Created by Charl-PC on 2016-10-16.
 */
public interface ContextResolver {

	ContextMap resolve(ContextDefinition contextDefinition, Map<String, Object> variables);
}
