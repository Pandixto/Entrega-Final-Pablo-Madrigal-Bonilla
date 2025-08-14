package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Carga consultas SQL etiquetadas con el formato:
 *   -- name: clave
 *   SELECT ...
 *   -- name: otra.clave
 *   ...
 *
 * Los archivos deben estar ubicados bajo resources/ y se cargan con ClassLoader.
 */
public final class SqlLoader {
    private SqlLoader() {}

    public static Map<String, String> loadNamedQueries(String resourcePath) {
        Map<String, String> map = new LinkedHashMap<>();
        try (var in = SqlLoader.class.getClassLoader().getResourceAsStream(resourcePath);
             var br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

            String line;
            String currentKey = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("-- name:")) {
                    // guardar bloque anterior
                    if (currentKey != null) {
                        map.put(currentKey, sb.toString().trim());
                        sb.setLength(0);
                    }
                    currentKey = line.substring(line.indexOf(":") + 1).trim();
                } else {
                    sb.append(line).append('\n');
                }
            }
            if (currentKey != null) {
                map.put(currentKey, sb.toString().trim());
            }
        } catch (Exception e) {
            throw new RuntimeException("No se pudo cargar el recurso SQL: " + resourcePath, e);
        }
        return map;
    }
}
