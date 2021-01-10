package Main.Game.ECS.Components;

import Main.Game.ECS.Entity.Component;

public class ManaBar extends Component
{
        private float maxMana;
        private float currentMana = maxMana;

        public ManaBar(float maxMana)
        {
            this.maxMana = maxMana;
        }

        public float getCurrentMana() {
            return currentMana;
        }

        public float getMaxMana() {
            return maxMana;
        }
}
