const msgprompt = {
  state: {
    prompts: {},
  },
  mutations: {
    // SET_PROMPTS: (state, prompts) => {
    //   state.prompts = prompts;
    // },
    SET_PROMPTS(state, payload) {
      state.prompts = JSON.parse(JSON.stringify(payload)); // 强制深拷贝
    },
  },
  actions: {
    getPrompt({ commit }, msg) {
      commit("SET_PROMPTS", msg);
    },
  },
};
export default msgprompt;
