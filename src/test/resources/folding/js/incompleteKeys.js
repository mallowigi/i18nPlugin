export const test = (i18n) => <fold text='{...}'>{
    i18n.t("test:ref.section");
    console.log(i18n.t("test:ref.section"));
    return i18n.t("test:ref.section2");
}</fold>;