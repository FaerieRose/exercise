import { ExercisePage } from './app.po';

describe('exercise App', () => {
  let page: ExercisePage;

  beforeEach(() => {
    page = new ExercisePage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
