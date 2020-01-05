package de.maxhenkel.car.entity.model.obj;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import joptsimple.internal.Strings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class OBJLoader {

    private static Map<ResourceLocation, OBJModel.OBJModelData> modelCache = new HashMap<>();

    public static OBJModel.OBJModelData load(ResourceLocation model) {
        try {
            return loadInternal(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static OBJModel.OBJModelData loadInternal(ResourceLocation model) throws IOException {
        if (modelCache.containsKey(model)) {
            return modelCache.get(model);
        }

        IResource resource = Minecraft.getInstance().getResourceManager().getResource(model);
        LineReader reader = new LineReader(resource);

        List<Vector3f> positions = Lists.newArrayList();
        List<Vec2f> texCoords = Lists.newArrayList();
        List<Vector3f> normals = Lists.newArrayList();
        List<int[][]> faces = Lists.newArrayList();

        String[] line;
        while ((line = reader.readAndSplitLine(true)) != null) {
            switch (line[0]) {
                case "v":
                    positions.add(net.minecraftforge.client.model.obj.OBJModel.parseVector4To3(line));
                    break;
                case "vt":
                    Vec2f vec2f = net.minecraftforge.client.model.obj.OBJModel.parseVector2(line);
                    texCoords.add(new Vec2f(vec2f.x, 1F - vec2f.y));
                    break;
                case "vn":
                    normals.add(net.minecraftforge.client.model.obj.OBJModel.parseVector3(line));
                    break;
                case "f":
                    int[][] vertices = new int[line.length - 1][];
                    for (int i = 0; i < vertices.length; i++) {
                        String vertexData = line[i + 1];
                        String[] vertexParts = vertexData.split("/");
                        int[] vertex = Arrays.stream(vertexParts).mapToInt(num -> Strings.isNullOrEmpty(num) ? 0 : Integer.parseInt(num)).toArray();
                        if (vertex[0] < 0) {
                            vertex[0] = positions.size() + vertex[0];
                        } else {
                            vertex[0]--;
                        }
                        if (vertex.length > 1) {
                            if (vertex[1] < 0) {
                                vertex[1] = texCoords.size() + vertex[1];
                            } else {
                                vertex[1]--;
                            }
                            if (vertex.length > 2) {
                                if (vertex[2] < 0) {
                                    vertex[2] = normals.size() + vertex[2];
                                } else {
                                    vertex[2]--;
                                }
                            }
                        }
                        vertices[i] = vertex;
                    }

                    faces.add(vertices);
                    break;
            }
        }

        OBJModel.OBJModelData obj = new OBJModel.OBJModelData(positions, texCoords, normals, faces);
        modelCache.put(model, obj);
        return obj;
    }

    public static class LineReader implements AutoCloseable {
        private InputStreamReader lineStream;
        private BufferedReader lineReader;

        public LineReader(IResource resource) {
            this.lineStream = new InputStreamReader(resource.getInputStream(), Charsets.UTF_8);
            this.lineReader = new BufferedReader(lineStream);
        }

        @Nullable
        public String[] readAndSplitLine(boolean ignoreEmptyLines) throws IOException {
            do {
                String currentLine = lineReader.readLine();
                if (currentLine == null) {
                    return null;
                }

                List<String> lineParts = new ArrayList<>();

                if (currentLine.startsWith("#")) {
                    currentLine = "";
                }

                if (currentLine.length() > 0) {
                    boolean hasContinuation;
                    do {
                        hasContinuation = currentLine.endsWith("\\");
                        String tmp = hasContinuation ? currentLine.substring(0, currentLine.length() - 1) : currentLine;

                        Arrays.stream(tmp.split("[\t ]+")).filter(s -> !Strings.isNullOrEmpty(s)).forEach(lineParts::add);

                        if (hasContinuation) {
                            currentLine = lineReader.readLine();
                            if (currentLine == null) {
                                break;
                            }

                            if (currentLine.length() == 0 || currentLine.startsWith("#")) {
                                break;
                            }
                        }
                    } while (hasContinuation);
                }

                if (lineParts.size() > 0) {
                    return lineParts.toArray(new String[0]);
                }
            } while (ignoreEmptyLines);

            return new String[0];
        }

        @Override
        public void close() throws Exception {
            lineReader.close();
            lineStream.close();
        }
    }
}
