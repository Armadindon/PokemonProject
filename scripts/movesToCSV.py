import requests
import os
import csv

if __name__ == "__main__":
    # Liste des abilités
    rq = requests.get("https://pokeapi.co/api/v2/move?limit=1000")

    with open("moves.csv", "w", newline="") as csvfile:
        csvWriter = csv.DictWriter(csvfile,
                                   fieldnames=["id", "name", "move_category", "accuracy", "effect_ailment",
                                               "effect_chance",
                                               "damage_class", "type", "power", "pp", "priority", "target",
                                               "stat_changes", "description"])

        csvWriter.writeheader()

        for results in rq.json()["results"]:
            jsonMove = requests.get(results["url"]).json()

            accuracy = jsonMove["accuracy"]
            effectChance = jsonMove["effect_chance"]
            power = jsonMove["power"]
            stat_changes = jsonMove["stat_changes"]

            # Si champ null, valeur par défaut
            if accuracy is None:
                accuracy = -1

            if effectChance is None:
                effectChance = 0

            if power is None:
                power = 0

            # Pour les changements de stats, on les formates
            if 0 == len(stat_changes):
                stat_changes = "No stat change"
            else:
                stat_changes = ""
                for i in range(0, len(jsonMove["stat_changes"])):
                    stat_changes += jsonMove["stat_changes"][i]["stat"]["name"] + ": " + str(
                        jsonMove["stat_changes"][i]["change"]) + ";"

            rowMove = {"id": jsonMove["id"], "name": jsonMove["name"],
                       "move_category": jsonMove["meta"]["category"]["name"], "accuracy": jsonMove["accuracy"],
                       "effect_ailment": jsonMove["meta"]["ailment"]["name"], "effect_chance": effectChance,
                       "damage_class": jsonMove["damage_class"]["name"], "type": jsonMove["type"]["name"],
                       "power": power, "pp": jsonMove["pp"], "priority": jsonMove["priority"],
                       "target": jsonMove["target"]["name"], "stat_changes": stat_changes,
                       "description": jsonMove["effect_entries"][0]["effect"]}

            print(rowMove)

            csvWriter.writerow(rowMove)
