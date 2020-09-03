import { AuthorModel } from './author.model';

export interface ReputationModel {
  monthlyBlogCount: number;
  monthlyKnolxCount: number;
  monthlyWebinarCount: number;
  monthlyTechHubCount: number;
  monthlyOsContributionCount: number;
  monthlyConferenceCount: number;
  monthlyResearchPaperCount: number;
  monthlyBookCount: number;
  allTimeBlogCount: number;
  allTimeKnolxCount: number;
  allTimeWebinarCount: number;
  allTimeTechHubCount: number;
  allTimeOsContributionCount: number;
  allTimeConferenceCount: number;
  allTimeBookCount: number;
  allTimeResearchPaperCount: number;
  reputation: AuthorModel[];
}
