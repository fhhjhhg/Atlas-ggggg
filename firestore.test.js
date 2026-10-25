const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_123";
const BOB_UID = "bob_456";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

test("Unauthenticated user: cannot read widget configs", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("widgetConfigs").get());
});

test("Authenticated user: cannot read another user's widget configs", async () => {
  await testEnv.withSecurityRulesDisabled(async (context) => {
    await context.firestore().collection("users").doc(BOB_UID).collection("widgetConfigs").doc("widget_exec_metrics").set({
      widgetId: "widget_exec_metrics",
      title: "Bob Metrics",
      subtitle: "Weekly Time-Saved",
      category: "SUMMARY_CARDS",
      widgetType: "EXECUTIVE_METRICS",
      isEnabled: true,
      order: 0,
      userId: BOB_UID,
    });
  });

  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  await assertFails(aliceDb.collection("users").doc(BOB_UID).collection("widgetConfigs").doc("widget_exec_metrics").get());
});

test("Authenticated user: can read and write their own widget config", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const aliceWidgetRef = aliceDb.collection("users").doc(ALICE_UID).collection("widgetConfigs").doc("widget_exec_metrics");

  await assertSucceeds(
    aliceWidgetRef.set({
      widgetId: "widget_exec_metrics",
      title: "1,000x Cyber-Physical Metrics",
      subtitle: "Weekly Time-Saved, Yield & Swarm Capacity",
      category: "SUMMARY_CARDS",
      widgetType: "EXECUTIVE_METRICS",
      isEnabled: true,
      order: 0,
      userId: ALICE_UID,
    })
  );

  await assertSucceeds(aliceWidgetRef.get());
});

test("Authenticated user: cannot write invalid widget config", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const aliceWidgetRef = aliceDb.collection("users").doc(ALICE_UID).collection("widgetConfigs").doc("invalid_widget");

  // Missing required fields
  await assertFails(
    aliceWidgetRef.set({
      widgetId: "invalid_widget",
      userId: ALICE_UID,
    })
  );
});
