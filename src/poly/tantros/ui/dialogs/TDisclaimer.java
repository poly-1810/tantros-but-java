package poly.tantros.ui.dialogs;

import arc.scene.ui.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.ui.dialogs.*;

public class TDisclaimer extends BaseDialog{
    public TDisclaimer(){
        super("@mod.poly-tantros.disclaimer.title");

        cont.add("@mod.poly-tantros.disclaimer.text")
            .width(500f)
            .wrap()
            .pad(4f)
            .get()
            .setAlignment(Align.center, Align.center);

        buttons.defaults().size(200f, 54f).pad(2f);
        setFillParent(false);

        TextButton b = buttons.button("@mod.poly-tantros.disclaimer.ok", Icon.ok, this::hide).get();
    }
}
