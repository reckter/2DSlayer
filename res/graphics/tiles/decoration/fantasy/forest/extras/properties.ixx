ixxFile {
    version = 4;
    name = "grass main properties";
}

default {
    name = "dead";
}

alternatives {
    length = 3;
    0 {
        name = "obelisk";
        chance = 0.01;
    }
    1 {
        name = "stone";
        chance = 0.01;
    }
    2 {
        name = "TARDIS";
        chance = 0.001;
    }
}