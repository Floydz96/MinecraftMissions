package com.gustgamer29;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gustgamer29.config.adapter.ConfigurationAdapter;
import com.gustgamer29.config.adapter.GsonConfigurationAdapter;
import com.gustgamer29.config.json.ConfigKeys;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class TesteMain {

    public static void main(String[] args) {
        System.out.println("Iniciando..");
        System.out.println(new Random().nextDouble() * 100);
//        TesteMain m = new TesteMain();
////        System.out.println(ConfigKeys.BLOCK_MINER_BLOCK_OCCURRENCE.get(new GsonConfigurationAdapter(null, Paths.get("teste.json"))));
//        ConfigurationAdapter adapter = new GsonConfigurationAdapter(null, Paths.get("teste.json"), "Configuration");
//
////        ConfigKeys.BLOCK_MINER_BLOCK_OCCURRENCE.get(adapter).forEach(blockOccurrence -> System.out.println(blockOccurrence.getId() + " " + blockOccurrence.getEasy() + " " + blockOccurrence.getNormal() + " " + blockOccurrence.getHard()));
//        ConfigKeys.BLOCK_MINER_BLOCK_WHITELIST.get(adapter).forEach((k, v) -> System.out.println("Mat " + k + " " + v.getId()));
//        m.load();
    }

    private void reload(){

    }

    private void load(){
        System.out.println("Carregando..");
        Path path = Paths.get("teste.json");
        if(!Files.exists(path)){
            save();
            return;
        }
        String line;
        try(BufferedReader br = Files.newBufferedReader(path)){
//            while((line = br.readLine()) != null){
//                System.out.println(line);
//            }
            JsonArray jarray = new GsonBuilder().disableHtmlEscaping().create().fromJson(br, JsonArray.class);
            JsonElement jele = jarray.get(0).getAsJsonObject().get("Configurations");

            System.out.println(jele.getAsJsonObject().get("BlockMiner").getAsJsonObject());

            //System.out.println(KeyTypes.NESTED_ELEMENTS.apply("General", jele));
            //JsonElement jar = jara.getAsJsonArray("General").get(0).getAsJsonObject().get("refresh-time");
//            jele.iterator().forEachRemaining(System.out::println);
//            System.out.println(jele.get(0).getAsJsonObject().has("General"));
//            System.out.println(KeyTypes.NESTED_ELEMENT.apply("BlockMiner.bounties.hard.reward-3.command", jele).toString());
            //System.out.println(ConfigKeys.BLOCK_MINER_REWARDS.get(jele));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save(){
        System.out.println("Criando novo arquivo..");

        File f = new File("teste.json");
        try {
            if(!f.createNewFile()){
                System.out.println("Arquivo não foi criado..");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void write(){
        System.out.println("Escrevendo no arquivo..");
    }
}
