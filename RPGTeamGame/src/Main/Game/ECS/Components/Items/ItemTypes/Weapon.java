package Main.Game.ECS.Components.Items.ItemTypes;

import Main.Game.ECS.Entity.GameObject;

public class Weapon
{
    private float damageModifier;
    private float wearValue; //durability it deals to the weapon on use
    private float coolDownValue;
    private GameObject onUseCreates;

}
