package br.ufrn.imd.genesigno.validation;

import br.ufrn.imd.genesigno.clustering.Dendogram;
import br.ufrn.imd.genesigno.core.Mediator;
import br.ufrn.imd.genesigno.model.Study;

public class Validator {
	public static boolean crossValidate() {
		
		Mediator m = Mediator.getInstance();
		Study study = m.getStudy();
		ValidationSignature sig = m.getDendogram().getValidationSignature();
		
		for (int i = 0; i < study.getSampleCount(); ++i) {
			Dendogram d = m.generateDendogramAndIgnore(i);
			if (!d.validateOver(sig)) return false;
		}
		return true;
	}
}
