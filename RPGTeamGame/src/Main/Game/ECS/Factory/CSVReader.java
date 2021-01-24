package Main.Game.ECS.Factory;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Entity.GameObject;

import java.lang.reflect.Constructor;

public class CSVReader
{
    private CSVReader READER = new CSVReader();
    private static String test = "Health";
    private static String para = "100";
    public CSVReader getREADER() {
        return READER;
    }
    private CSVReader()
    {

    }

    public static void Read()
    {
        try
        {
            //name == EntityName
            GameObject g = new GameObject("yo");
            //loop for each component
            Class myClass = Class.forName(test);

            //get all the parameters as array
            //get array of parameters class
            Constructor constructor = myClass.getConstructor(Integer.valueOf(para).getClass());

            Object[] parameters = {Integer.valueOf(para)};
            Object instanceOfMyClass = constructor.newInstance(parameters);
            g.addComponent((Component)instanceOfMyClass);
        }
        catch(Exception e)
        {

        }




    }


}
