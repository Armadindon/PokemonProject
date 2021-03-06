import requests
import os
import csv

if __name__ == "__main__":

    try:
        os.mkdir("sprites")
    except Exception as e:
        print("Le dossier imgs existe deja")

    with open("pokemons.csv", "w", newline="") as csvfile:
        lst = requests.get("https://pokeapi.co/api/v2/pokemon/?limit=807")  # récupère la liste de pokémons
        csvWriter = csv.DictWriter(csvfile,
                                   fieldnames=["id", "name", "base_experience", "height", "weight", "spriteFront",
                                               "spriteBack", "speed", "spDefense", "spAttack", "defense", "attack",
                                               "hp", "type1", "type2", "learnableMove","description"])
        csvWriter.writeheader()

        for results in lst.json()["results"]:
            Infos = {}
            jsonPokemon = requests.get(results["url"]).json()
            Infos = {"id": jsonPokemon["id"], "name": jsonPokemon["species"]["name"],
                     "base_experience": jsonPokemon["base_experience"], "height": jsonPokemon["height"],
                     "weight": jsonPokemon["weight"], "spriteFront": "sprites/" + str(jsonPokemon["id"]) + ".png",
                     "spriteBack": "sprites/" + str(jsonPokemon["id"]) + "_back.png",
                     "speed": jsonPokemon["stats"][0]["base_stat"], "spDefense": jsonPokemon["stats"][1]["base_stat"],
                     "spAttack": jsonPokemon["stats"][2]["base_stat"], "defense": jsonPokemon["stats"][3]["base_stat"],
                     "attack": jsonPokemon["stats"][4]["base_stat"], "hp": jsonPokemon["stats"][5]["base_stat"],
                     "type1": jsonPokemon["types"][0]["type"]["name"], "learnableMove": ""}

            for moves in jsonPokemon[
                "moves"]:  # Permet de rajouter l'id de tous les attaques que le pokémon peut apprendre
                Infos["learnableMove"] += moves["move"]["url"].replace("https://pokeapi.co/api/v2/move/", "").replace(
                    "/", "") + ";"

            if len(jsonPokemon["types"]) != 2:
                Infos["type2"] = "NULL"
            else:
                Infos["type2"] = jsonPokemon["types"][1]["type"]["name"]

            # On télécharge les sprites

            try:
                rqFront = requests.get(jsonPokemon["sprites"]["front_default"])

                with open("sprites/" + str(jsonPokemon["id"]) + ".png", "wb") as f:
                    for chunk in rqFront.iter_content(1024):
                        f.write(chunk)

                rqBack = requests.get(jsonPokemon["sprites"]["back_default"])

                with open("sprites/" + str(jsonPokemon["id"]) + "_back.png", "wb") as f:
                    for chunk in rqBack.iter_content(1024):
                        f.write(chunk)


            except Exception as e:
                print("Erreur de download des sprites de l'id " + str(jsonPokemon["id"]))
                Infos["spriteBack"] = "NULL"

            rqDescription = requests.get(jsonPokemon["species"]["url"])

            for entries in rqDescription.json()["flavor_text_entries"]:

                if entries["language"]["name"] == "en":
                    Infos["description"] =  entries["flavor_text"].replace("\n","")
                    break

            print(Infos)
            csvWriter.writerow(Infos)

        csvWriter.writerow({
            "id" : 808,
            "name" : "Maximator",
            "base_experience" : 10000,
            "height" : 169,
            "weight" : 49,
            "spriteFront" : "sprites/808.png",
            "spriteBack": "NULL",
            "speed" : 110,
            "spDefense" : 65,
            "spAttack": 70,
            "defense" : 40,
            "attack" : 35,
            "hp" : 75,
            "type1" : "normal",
            "type2" : "fairy",
            "learnableMove" : "33;57;67;290;",
            "description" : "The legends says that Maximator will come eat children at night without taking any Kg, he love that people call him 'Kwaac'"
        })
