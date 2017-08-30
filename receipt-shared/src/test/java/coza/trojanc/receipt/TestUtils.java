package coza.trojanc.receipt;

import coza.trojanc.receipt.context.*;
import coza.trojanc.receipt.context.impl.DefaultContextMap;
import coza.trojanc.receipt.context.impl.SimpleContextDefinition;
import coza.trojanc.receipt.context.impl.SimpleContextVariable;
import coza.trojanc.receipt.context.test.SoldItem;
import coza.trojanc.receipt.context.test.TestTransaction;
import coza.trojanc.receipt.shared.Align;
import coza.trojanc.receipt.template.PrintTemplate;
import coza.trojanc.receipt.template.builder.PrintTemplateBuilder;
import coza.trojanc.receipt.template.process.ProcessedTemplate;
import coza.trojanc.receipt.template.process.TemplateProcessor;
import coza.trojanc.receipt.template.process.impl.DefaultTemplateProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Charl Thiem
 */
public class TestUtils {

	/*
	  Inputs for the context
	  */
	public static final String INPUT_TRANSACTION = "transaction";

	/*
	  Expressions for items to be resolved from inputs
	  */
	public static final String EXPR_TRADER_NAME = "transaction.traderName";
	public static final String EXPR_NUM_ITEMS = "transaction.numItems";
	public static final String EXPR_TRANSACTION_DATE = "transaction.transactionDate";
	public static final String EXPR_SOLD_ITEMS_VALUE = "transaction.soldItems[].value";
	public static final String EXPR_SOLD_ITEMS_NAME = "transaction.soldItems[].name";

	/*
	  Keys for items resolved in the context
	*/
	public static final String CTX_TRADER_NAME = "traderName";
	public static final String CTX_NUM_ITEMS = "numItems";
	public static final String CTX_TRANSACTION_DATE = "transactionDate";
	public static final String CTX_SOLD_ITEMS_VALUE = "soldItems[].value";
	public static final String CTX_SOLD_ITEMS_VALUE_1 = "soldItems[0].value";
	public static final String CTX_SOLD_ITEMS_VALUE_2 = "soldItems[1].value";
	public static final String CTX_SOLD_ITEMS_NAME = "soldItems[].name";
	public static final String CTX_SOLD_ITEMS_NAME_1 = "soldItems[0].name";
	public static final String CTX_SOLD_ITEMS_NAME_2 = "soldItems[1].name";
	public static final String CTX_SOLD_ITEMS_LENGTH = "soldItems[].$$length";
	public static final String DATE_FORMAT = "YYYY-MM-dd";

	private static SimpleContextDefinition contextDefinition;
	private static Map<String, Object> contextVariables;
	private static ContextMap resolvedVariables;

	public static ContextDefinition createContextDefinition(){
		if(contextDefinition == null) {
			contextDefinition = new SimpleContextDefinition();
			SimpleContextVariable cd;

			// Name
			cd = new SimpleContextVariable();
			cd.setType(DynamicType.String);
			cd.setExpression(EXPR_TRADER_NAME);
			cd.setKey(CTX_TRADER_NAME);
			contextDefinition.addVariable(cd);

			// Age
			cd = new SimpleContextVariable();
			cd.setType(DynamicType.Number);
			cd.setExpression(EXPR_NUM_ITEMS);
			cd.setKey(CTX_NUM_ITEMS);
			contextDefinition.addVariable(cd);

			// Birth
			cd = new SimpleContextVariable();
			cd.setType(DynamicType.Date);
			cd.setFormatting(DATE_FORMAT);
			cd.setExpression(EXPR_TRANSACTION_DATE);
			cd.setKey(CTX_TRANSACTION_DATE);
			contextDefinition.addVariable(cd);

			cd = new SimpleContextVariable();
			cd.setType(DynamicType.Decimal);
			cd.setFormatting("#0.00");
			cd.setExpression(EXPR_SOLD_ITEMS_VALUE);
			cd.setKey(CTX_SOLD_ITEMS_VALUE);
			contextDefinition.addVariable(cd);

			cd = new SimpleContextVariable();
			cd.setType(DynamicType.String);
			cd.setExpression(EXPR_SOLD_ITEMS_NAME);
			cd.setKey(CTX_SOLD_ITEMS_NAME);
			contextDefinition.addVariable(cd);
		}

		return contextDefinition;
	}



	public static Map<String, Object> createContextVariables(){
		if(contextVariables == null) {
			Map<String, Object> variables = new HashMap<>();
			variables.put(INPUT_TRANSACTION, new TestTransaction());
			contextVariables = Collections.unmodifiableMap(variables);
		}
		return contextVariables;
	}


	public static ContextMap createResolvedVariables(){
		if(resolvedVariables == null) {
			resolvedVariables = new DefaultContextMap();
			resolvedVariables.add(CTX_SOLD_ITEMS_LENGTH,"2");
			resolvedVariables.add(CTX_SOLD_ITEMS_VALUE_2, Double.toString(SoldItem.SOLD_ITEM2_VALUE));
			resolvedVariables.add(CTX_SOLD_ITEMS_NAME_1,SoldItem.SOLD_ITEM1_NAME);
			resolvedVariables.add(CTX_SOLD_ITEMS_VALUE_1 ,Double.toString(SoldItem.SOLD_ITEM1_VALUE));
			resolvedVariables.add(CTX_SOLD_ITEMS_NAME_2,SoldItem.SOLD_ITEM2_NAME);
			resolvedVariables.add(CTX_TRANSACTION_DATE,"2016-03-15");
			resolvedVariables.add(CTX_TRADER_NAME, TestTransaction.VALUE_TRADERNAME);
			resolvedVariables.add(CTX_NUM_ITEMS , Integer.toString(TestTransaction.VALUE_NUM_ITEMS));
		}
		return resolvedVariables;
	}

	public static ProcessedTemplate getProcessedTemplate(){
		TemplateProcessor processor = new DefaultTemplateProcessor();
		return processor.process(TestUtils.createTemplate(), TestUtils.createResolvedVariables());
	}

	public static PrintTemplate createTemplate(){
		return new PrintTemplateBuilder().name("Test Template")
				.line()
				.dynamicText(CTX_TRADER_NAME).align(Align.CENTER)
				.fillLine('-')
				.line()
				.text("Date ").align(Align.LEFT)
				.dynamicText(CTX_TRANSACTION_DATE).align(Align.LEFT).offset(5)
				.line()
				.text("SHIFT").align(Align.LEFT)
				.text("12").align(Align.RIGHT).offset(10)
				.text("TX").align(Align.RIGHT).offset(-7)
				.text("123").align(Align.RIGHT).offset(-2)
				.fillLine('-')
				.line()
				.text("Items:")
				.dynamicText(CTX_SOLD_ITEMS_LENGTH).align(Align.RIGHT)
				.repeat("soldItems")
				.line()
				.dynamicText(".name").align(Align.LEFT)
				.dynamicText(".value").align(Align.RIGHT)
				.end()
				.fillLine('-')
				.feed()
				.build();
	}

	public static OutputStream TEST_OUT = new OutputStream() {
		@Override
		public void write(int b) throws IOException {
			System.out.write(b);
		}
	};

}