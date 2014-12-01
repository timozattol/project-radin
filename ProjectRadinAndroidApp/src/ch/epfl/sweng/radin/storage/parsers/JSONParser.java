package ch.epfl.sweng.radin.storage.parsers;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.radin.storage.Model;

/**
 * @author timozattol
 * An Interface to represent a two-direction parser:
 * Capable of parsing a JSON-encoded model into the "real" model, 
 * and capable of encoding the "real" model into JSON
 * @param <M> the "real" Model type
 */
public interface JSONParser<M extends Model> {
    List<M> getModelsFromJson(JSONObject jsonList) throws JSONException;
    JSONObject getJsonFromModels(List<M> modelList) throws JSONException;
}
