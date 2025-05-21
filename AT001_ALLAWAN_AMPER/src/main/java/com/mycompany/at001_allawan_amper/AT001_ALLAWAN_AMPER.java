package com.mycompany.at001_allawan_amper;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

public class AT001_ALLAWAN_AMPER {

    public static void main(String[] args) {

        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        LinkedList<String> firstName = new LinkedList<>(Arrays.asList(
                "James", "Reino", "Mitch", "Ghynne", "Lee", "Ann", "Joshua", "Adriane", "Excel", "Reyian",
                "Nico", "Ivina", "Blaise", "Alleah", "Marie", "Qiann", "Tans", "SuperHuman", "Anton", "Mian",
                "Christian", "Justin", "Franzen", "Danica", "Joy", "Vinze", "Louie", "Jay", "Matt", "Matthew",
                "Ikee", "Benedict", "Mar", "John", "Jann", "Jayce", "Bruce", "Angelo", "Jay", "Daryl",
                "Dave", "Noel", "Rex", "Eduardo", "Karl", "Edziel", "Gabriele", "Lebron", "Jerhald", "Khalil"
        ));

        LinkedList<String> lastName = new LinkedList<>(Arrays.asList(
                "Allawan", "Eucare", "Canja", "Yuson", "Lerasan", "Mahipus", "Militares", "Ariego", "Dimapilis", "Vergara",
                "Calvez", "Brua", "Yukare", "Attos", "Pelpinosas", "Perin", "Reyes", "Lulab", "Alconera", "Espera",
                "Crispino", "Sanchez", "Abad", "Rivera", "Nanual", "Gatan", "Villacampa", "Galorio", "Ramos", "Salibay",
                "Millondaga", "Enola", "Lohera", "Espiritu", "Fuetes", "Orzales", "Mendoza", "Roswell", "Saquilon", "Tanio",
                "Toroba", "Verginiza", "Baco", "Ambrosio", "Domingo", "Cain", "Carido", "Villegas", "Rada", "Lee"
        ));

        Random randomizer = new Random();
        boolean exitGame = false;

        // --- ENCOUNTER ---
        while (!exitGame && !firstName.isEmpty() && !lastName.isEmpty()) {
            encounter(firstName, lastName, random, scanner);

            // --- AFTER BATTLE CHECK ---
            if (!exitGame && (!firstName.isEmpty() && !lastName.isEmpty())) {
                while (true) {
                    System.out.print("Do you want to continue to fight another monster? (yes/exit): ");
                    String nextAction = scanner.next();
                    if (nextAction.equalsIgnoreCase("exit")) {
                        System.out.println("You exited the game.");
                        exitGame = true;
                        break;
                    } else if (nextAction.equalsIgnoreCase("yes")) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please type 'yes' to continue or 'exit' to quit.");
                    }
                }
            } else if (firstName.isEmpty() || lastName.isEmpty()) {
                System.out.println("No more unique monster names left. You have defeated all possible monsters!");
                exitGame = true;
            }
        }

        System.out.println("Game Over.");
    }

    // --- ENCOUNTER METHOD ---
    public static void encounter(
            LinkedList<String> firstNames,
            LinkedList<String> lastNames,
            Random random,
            Scanner scanner
    ) {
        // --- MONSTER NAME GENERATION ---
        int firstIndex = random.nextInt(firstNames.size());
        int lastIndex = random.nextInt(lastNames.size());
        String monsterFirst = firstNames.remove(firstIndex);
        String monsterLast = lastNames.remove(lastIndex);
        String monsterName = monsterFirst + " " + monsterLast;

        // --- STATS ---
        int playerHp = 100;
        int playerMaxHp = 100;
        int monsterHp = 100;

        int playerMinDmg = 5;
        int playerMaxDmg = 10;

        int monsterMinDmg = 5;
        int monsterMaxDmg = 10;

        // --- MONSTER STATUS ---
        boolean monsterStunned = false;
        boolean desperateGambitActive = false;
        boolean desperateGambitNerf = false;
        boolean desperateGambitUsed = false;

        // --- PLAYER BUFFS & PASSIVES ---
        Stack<Integer> monsterHpStack = new Stack<>();
        Stack<Integer> desperateGambitStack = new Stack<>();
        Stack<Integer> jinguBuffStack = new Stack<>();
        int jinguHitCounter = 0;

        // --- PLAYER SKILL STATE ARRAYS ---
        int[] echoesOfPain = new int[2];
        echoesOfPain[0] = 0;
        echoesOfPain[1] = 0;

        int[] stunCooldown = new int[2];
        stunCooldown[0] = 0;
        stunCooldown[1] = 0;

        System.out.println("YOU ENCOUNTERED AN ENEMY: " + monsterName + "!");

        OUTER:
        while (true) {
            System.out.println("Player Hp: " + playerHp);
            System.out.println(monsterName + " Hp: " + monsterHp);
            System.out.println();
            if (playerHp <= 0 && monsterHp <= 0) {
                System.out.println("It's a draw!");
                break;
            } else if (playerHp <= 0) {
                System.out.println("You lost! " + monsterName + " wins!");
                break;
            } else if (monsterHp <= 0 && !desperateGambitActive && !desperateGambitNerf && !desperateGambitUsed) {
                System.out.println("Congratulations! You defeated " + monsterName + "!");
                break;
            }
            System.out.println("PLAYER'S TURN!");
            System.out.println("1. Normal Attack");
            String stunStatus = (stunCooldown[0] == 1 ? "Cooldown: " + stunCooldown[1] : "Ready");
            System.out.println("2. Stun Attack (" + stunStatus + ")");
            System.out.println("3. Skip Turn");
            String doomStatus = (echoesOfPain[0] == 2) ? ("Cooldown: " + echoesOfPain[1]): (echoesOfPain[0] == 1 ? ("Active: " + echoesOfPain[1] + " turn(s) left"): "Ready");
            System.out.println("4. Activate Echoes of Pain (" + doomStatus + ")");
            System.out.print("Choose (or type exit): ");
            String input = scanner.next();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("You exited the game.");
                break;
            }
            // --- PLAYER ACTIONS ---
            switch (input) {
                case "1" -> {
                    // Normal Attack and Jingu Mastery
                    jinguHitCounter++;
                    int playerDmg = playerMinDmg + random.nextInt(playerMaxDmg - playerMinDmg + 1);
                    if (jinguBuffStack.isEmpty() && jinguHitCounter == 4) {
                        System.out.println("Passive Jingu Mastery is activated!");
                        System.out.println("Jingu Mastery activated! Next 4 normal attacks gain 160% bonus damage and 80% lifesteal!");
                        for (int i = 0; i < 4; i++) {
                            jinguBuffStack.push(1);
                        }
                        jinguHitCounter = 0;
                    }
                    if (!jinguBuffStack.isEmpty()) {
                        int bonusDmg = (int) Math.round(playerDmg * 1.6);
                        playerDmg += bonusDmg;
                        int lifesteal = (int) Math.round(playerDmg * 0.80);

                        // --- LIFESTEAL CANNOT EXCEED MAX HP ---
                        if (playerHp + lifesteal > playerMaxHp) {
                            lifesteal = playerMaxHp - playerHp;
                            if (lifesteal < 0) {
                                lifesteal = 0;
                            }
                            playerHp = playerMaxHp;
                        } else {
                            playerHp += lifesteal;
                        }
                        System.out.println("Jingu Mastery buff: +160% bonus (" + bonusDmg + ") damage and +" + lifesteal + " HP (lifesteal)!");
                        jinguBuffStack.pop();
                    }
                    System.out.println("You dealt " + playerDmg + " damage to " + monsterName + ".");
                    monsterHpStack.push(monsterHp);
                    monsterHp -= playerDmg;
                    if (monsterHp < 0) {
                        monsterHp = 0;
                    }
                    System.out.println(monsterName + " remaining hp: " + monsterHp);
                    System.out.println();
                    // Desperate Gambit passive
                    if (monsterHp <= 0 && !desperateGambitActive && !desperateGambitNerf && !desperateGambitUsed) {
                        int chance = random.nextInt(1);
                        if (chance == 0) {
                            System.out.println(monsterName + "'s passive activates: 'Desperate Gambit'!");
                            monsterHp = 50;
                            System.out.println(monsterName + " regains 50% HP: " + monsterHp);
                            System.out.println(monsterName + " is empowered for 3 turns! ( +10% damage )");
                            desperateGambitActive = true;
                            desperateGambitStack.push(3);
                            desperateGambitUsed = true;
                            monsterMinDmg = 5;
                            monsterMaxDmg = 15;
                        } else {
                            System.out.println("Congratulations! You defeated " + monsterName + "!");
                            break OUTER;
                        }
                    }
                }
                case "2" -> {
                    // Stun Attack and cooldown
                    if (stunCooldown[0] == 1) {
                        System.out.println("Stun Attack is on cooldown! (" + stunCooldown[1] + " turn(s) left)");
                        System.out.println();
                    } else {
                        jinguHitCounter = 0;
                        int stunChance = random.nextInt(4);
                        if (stunChance == 0) {
                            monsterStunned = true;
                            System.out.println("Stun successful! " + monsterName + " is stunned and will skip its turn.");
                        } else {
                            System.out.println("Stun failed! " + monsterName + " is not stunned.");
                        }
                        stunCooldown[0] = 1;
                        stunCooldown[1] = 4;
                        System.out.println();
                    }
                }
                case "3" -> {
                    // Skip Turn
                    jinguHitCounter = 0;
                    System.out.println("You skipped your turn!");
                    System.out.println();
                }
                case "4" -> {
                    // Echoes of Pain skill
                    switch (echoesOfPain[0]) {
                        case 1 ->
                            System.out.println("Echoes of Pain is already active! (" + echoesOfPain[1] + " turn(s) left)");
                        case 2 ->
                            System.out.println("Echoes of Pain is on cooldown! (" + echoesOfPain[1] + " turn(s) left)");
                        default -> {
                            echoesOfPain[0] = 1;
                            echoesOfPain[1] = 3;
                            System.out.println("You activate DOOM REFLECTION! Next 3 attacks will be reflected.");
                        }
                    }
                    System.out.println();
                }
                default -> {
                    System.out.println("Invalid input! Please enter 1, 2, 3, 4 or 'exit'.");
                    System.out.println();
                    continue;
                }
            }
            if (stunCooldown[0] == 1) {
                stunCooldown[1]--;
                if (stunCooldown[1] <= 0) {
                    stunCooldown[0] = 0;
                    stunCooldown[1] = 0;
                    System.out.println("Stun Attack cooldown finished! Skill is ready to use again.");
                }
            }
            if (echoesOfPain[0] == 2) {
                echoesOfPain[1]--;
                if (echoesOfPain[1] <= 0) {
                    echoesOfPain[0] = 0;
                    echoesOfPain[1] = 0;
                    System.out.println("Echoes of Pain cooldown finished! Skill is ready to use again.");
                }
            }
            if (monsterHp > 0) {
                if (monsterStunned) {
                    // If stunned, monster skips this turn
                    System.out.println(monsterName + " is stunned and cannot attack this turn.");
                    System.out.println();
                    monsterStunned = false;
                } else {
                    int monsterDmg = monsterMinDmg + random.nextInt(monsterMaxDmg - monsterMinDmg + 1);

                    // Desperate Gambit empowered/nerfed 
                    if (desperateGambitActive && !desperateGambitStack.isEmpty()) {
                        monsterDmg = monsterDmg + (monsterDmg * 10 / 100);
                        int turnsLeft = desperateGambitStack.pop() - 1;
                        if (turnsLeft > 0) {
                            desperateGambitStack.push(turnsLeft);
                        } else {
                            desperateGambitActive = false;
                            desperateGambitNerf = true;
                            System.out.println("Desperate Gambit's power fades. " + monsterName + " now deals half damage and its max damage returns to normal!");
                            monsterMinDmg = 5;
                            monsterMaxDmg = 10;
                        }
                    } else if (desperateGambitNerf) {
                        monsterDmg = monsterDmg / 2;
                    }

                    // Echoes of Pain skill 
                    if (echoesOfPain[0] == 1 && echoesOfPain[1] > 0) {
                        int reflectRoll = random.nextInt(4);
                        if (reflectRoll == 0) {
                            System.out.println("DOOM REFLECTION: Perfect reflect! " + monsterName + " takes " + monsterDmg + " damage, you take 0!");
                            monsterHp -= monsterDmg;
                            if (monsterHp < 0) {
                                monsterHp = 0;
                            }
                        } else {
                            int reflected = monsterDmg / 2;
                            System.out.println("DOOM REFLECTION: You reflect " + reflected + " damage back! You take " + monsterDmg + " damage.");
                            monsterHp -= reflected;
                            playerHp -= monsterDmg;
                            if (playerHp < 0) {
                                playerHp = 0;
                            }
                            if (monsterHp < 0) {
                                monsterHp = 0;
                            }
                        }
                        echoesOfPain[1]--;
                        if (echoesOfPain[1] <= 0) {
                            echoesOfPain[0] = 2;
                            echoesOfPain[1] = 6;
                            System.out.println("Echoes of Pain has ended! Cooldown started.");
                        } else {
                            System.out.println("Echoes of Pain remains active for " + echoesOfPain[1] + " more turn(s).");
                        }
                        System.out.println();
                    } else {
                        // Normal monster attack
                        System.out.println(monsterName.toUpperCase() + "'S TURN!");
                        System.out.println(monsterName + " attacks and deals " + monsterDmg + " damage to you.");
                        playerHp -= monsterDmg;
                        if (playerHp < 0) {
                            playerHp = 0;
                        }
                        System.out.println("Your remaining hp: " + playerHp);
                        System.out.println();
                    }

                    // Monster Regeneration passive
                    int regenChance = random.nextInt(4);
                    if (!monsterHpStack.isEmpty() && regenChance == 0) {
                        int prevHp = monsterHpStack.peek();
                        System.out.println(monsterName + " activates passive: 'Regeneration'!");
                        System.out.println("Regeneration: " + monsterName + " restores its HP to " + prevHp);
                        monsterHp = prevHp;
                        System.out.println();
                    }
                }
            }
            if (monsterHp <= 0) {
                System.out.println("Congratulations! You defeated " + monsterName + "!");
                break;
            }
            if (playerHp <= 0 && monsterHp <= 0) {
                System.out.println("It's a draw!");
                break;
            } else if (playerHp <= 0) {
                System.out.println("You lost! " + monsterName + " wins!");
                break;
            }
        }
    }
                public static void travel(String action)
                    Random random;
                int chance;
                
                if(action.contentEquals("2")){
                    //going north
                    location[0[1]++;]
                }
}
