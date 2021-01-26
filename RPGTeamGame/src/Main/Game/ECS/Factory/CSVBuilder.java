package Main.Game.ECS.Factory;

import Main.Game.ECS.Components.*;
import Main.Game.ECS.Components.ComponentENUMs.InputTypes;
import Main.Game.ECS.Components.ComponentENUMs.MovementTypes;
import Main.Game.ECS.Components.ItemComponents.Pickup;
import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.ECS.Components.SpecialComponents.CollisionEvent;
import Main.Game.ECS.Components.SpecialComponents.Light;
import Main.Game.ECS.Components.SpecialComponents.Particles;
import Main.Game.ECS.Components.StandardComponents.*;
import Main.Game.ECS.Components.StatComponents.*;

import Main.Game.ECS.Entity.GameObject;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector3f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVBuilder {

    public static GameObject build (String objectName, Object... valuesToSwitch){
        File file  = new File("Assets//GameStats//EntityStats.txt");
        Scanner scan = null;
        GameObject object = null;
        int valueSwitchCounter = 0;

        List valuesList = new ArrayList();
        for (Object o : valuesToSwitch)
        {
            if (o instanceof Integer || o instanceof Float || o instanceof Boolean) valuesList.add(o.toString());
            else valuesList.add(o);

        }

        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String outputString = "";
        while (scan.hasNext()) {
            outputString += scan.next();
        }

        String[] outputStringSplit = outputString.split(":");
        for (int i = 1; i < outputStringSplit.length; i++) {
            String header = outputStringSplit[i].substring(0, outputStringSplit[i].indexOf(";"));

            boolean test = header.equals(objectName);

            if (header.equals(objectName)) {
                object = new GameObject(header);
                String[] record = outputStringSplit[i].split(";");
                for  (int j = 1; j < record.length; j++) {
                    String inputClass = record[j].substring(0, record[i].indexOf(","));
                    Object[] values = record[j].substring(record[i].indexOf(",") + 1).split(",");

                    for (int n = 0; n < values.length; n++) {
                        if (((String) values[n]).equals("%")) {
                            values[n] = valuesList.get(valueSwitchCounter);
                            valueSwitchCounter++;
                        }
                    }

                    giveComponent(object, inputClass, values);
                }
            }
        }
        return object;
    }

    private static void giveComponent(GameObject object, String inputClass, Object ... Values) {
        List valueList = new ArrayList();
        for (Object o: Values) {
            valueList.add(o);
        }

        // TODO: IF NEW COMPONENT IS ADDED PLEASE ADD TO THIS SWITCH LIST
        switch(inputClass) {
            case "Abilities" :
                // To Be Added Later When Class Is More Developed
                break;

            case "Animation" :
                object.addComponent(
                        new Animation(
                                Float.parseFloat((String) valueList.get(0))));
                break;

            case "Backpack" :
                object.addComponent(
                        new Backpack(
                            Integer.parseInt((String) valueList.get(0)),
                            Boolean.parseBoolean((String) valueList.get(1))));
                break;

            case "Collider" :
                object.addComponent(
                        new Collider(
                                Boolean.parseBoolean((String) valueList.get(0)),
                                Boolean.parseBoolean((String) valueList.get(1)),
                                Boolean.parseBoolean((String) valueList.get(2)),
                                Boolean.parseBoolean((String) valueList.get(3))));
                break;

            case "CollisionEvent" :
                object.addComponent(
                        new CollisionEvent(
                                object));
                break;

            case "EffectComponent" :
                object.addComponent(
                        new EffectComponent());
                break;

            case "EffectEvent" :
                // To Be Added Later When Class Is More Developed
                break;

            case "Inputs" :
                Component inputComp;
                if (((String) valueList.get(0)).equals("HUMAN")) inputComp = new Inputs(InputTypes.HUMAN);
                else inputComp = new Inputs(InputTypes.AI);

                object.addComponent(inputComp);
                break;

            case "Level" :
                object.addComponent(
                        new Level(
                                Float.parseFloat((String) valueList.get(0))));
                break;

            case "Light" :
                object.addComponent(new Light(
                        Float.parseFloat((String) valueList.get(0)),
                        Float.parseFloat((String) valueList.get(1)),
                        new Vector3f(
                                Float.parseFloat((String) valueList.get(2)),
                                Float.parseFloat((String) valueList.get(3)),
                                Float.parseFloat((String) valueList.get(4)))));
                break;

            case "Particles" :
                object.addComponent(
                        new Particles(
                                Float.parseFloat((String) valueList.get(0))));
                break;

            case "Pickup" :
                object.addComponent(
                        new Pickup(
                                ((GameObject) valueList.get(0)),
                                Float.parseFloat((String) valueList.get(1))));
                break;

            case "Position" :
                object.addComponent(
                        new Position(
                            new Vector2f(
                                Float.parseFloat((String) valueList.get(0)),
                                Float.parseFloat((String) valueList.get(1))),
                            object));
                break;

            case "TextureComponent" :
                // Conformation needed
                break;

            case "TransformComponent" :
                object.addComponent(
                        new TransformComponent(
                                new Vector2f(
                                        Float.parseFloat((String) valueList.get(0)),
                                        Float.parseFloat((String) valueList.get(1)))));
                break;

            case "XPBar" :
                object.addComponent(
                        new XPBar(
                                Float.parseFloat((String) valueList.get(0))));
                break;

            case "Armor" :
                object.addComponent(
                        new Armor(
                                Float.parseFloat((String) valueList.get(0))));
                break;

            case "Health" :
                object.addComponent(
                        new Health(
                                Float.parseFloat((String) valueList.get(0))));
                break;

            case "Mana" :
                object.addComponent(
                        new Mana(
                            Float.parseFloat((String) valueList.get(0))));
                break;

            case "Speed" :
                Component speedComp;
                if (((String) valueList.get(0)).equals("CONTROLLED"))
                    speedComp = new Speed(MovementTypes.CONTROLLED, Float.parseFloat((String) valueList.get(0)));
                else speedComp = new Speed(MovementTypes.LINEAR, Float.parseFloat((String) valueList.get(0)));

                object.addComponent(speedComp);
                break;

            case "Strength" :
                object.addComponent(
                        new Strength(
                                Float.parseFloat((String) valueList.get(0))));
                break;

            case "Wisdom" :
                object.addComponent(
                        new Wisdom(
                                Float.parseFloat((String) valueList.get(0))));
                break;
        }
        System.out.println("Test");
    }
}

