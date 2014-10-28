package ch.epfl.sweng.radin.storage;

import java.util.List;

import org.json.JSONObject;

/**
 * @author timozattol
 * An Interface to represent a two-direction parser:
 * Capable of parsing a JSON-encoded model into the "real" model, 
 * and capable of encoding the "real" model into JSON
 * @param <M> the "real" Model type
 */
public interface JSONParser<M extends Model> {
    List<M> getModelsFromJson(List<JSONObject> jsonList);
    List<JSONObject> getJsonFromModels(List<M> modelList);
}
