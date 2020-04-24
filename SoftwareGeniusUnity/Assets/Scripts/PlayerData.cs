using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerData
{
    
    public static int standingOn=3;//0-23
    public LandStatus[] progress = new LandStatus[]{
        new LandStatus(true, "Angel", "EASY"),
        new LandStatus(true, "Collins", "HARD"),
        new LandStatus(true, "Angel", "MEDIUM"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        new LandStatus(false, "No owner yet", "Haven't chosen yet"),
        };

}

[System.Serializable]
public class LandStatus
{
    public bool Explored { get; set; }
    public string OwnerName { get; set; }
    public string Difficulty { get; set; }
    public LandStatus(bool explored, string ownername, string difficulty)
    {
        Explored = explored;
        OwnerName = ownername;
        Difficulty = difficulty;
        // Explored = false;
        //OwnerName = "No owner yet";
        //Difficulty = "Haven't chosen yet";
    }

}

